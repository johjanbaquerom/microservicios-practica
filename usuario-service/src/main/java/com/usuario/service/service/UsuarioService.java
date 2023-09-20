package com.usuario.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entity.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeingClient;
import com.usuario.service.models.Carro;
import com.usuario.service.models.Moto;
import com.usuario.service.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CarroFeignClient carroFeignClient;

	@Autowired
	private MotoFeingClient motoFeingClient;

	public List<Usuario> getAll() {
		return usuarioRepository.findAll();
	}

	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	public Usuario save(Usuario usuario) {
		Usuario nuevoUsuario = usuarioRepository.save(usuario);
		return nuevoUsuario;
	}

	public List<Carro> getCarros(int UsuarioId) {
		List<Carro> carros = restTemplate.getForObject("http://localhost:8082/carro/usuario/" + UsuarioId, List.class);
		return carros;
	}

	public List<Moto> getMotos(int UsuarioId) {
		List<Moto> motos = restTemplate.getForObject("http://localhost:8083/moto/usuario/" + UsuarioId, List.class);
		return motos;
	}

	public Carro saveCarro(int usuarioId, Carro carro) {
		carro.setUsuarioId(usuarioId);
		Carro nuevoCarro = carroFeignClient.save(carro);
		return nuevoCarro;
	}

	public Moto saveMoto(int usuarioId, Moto moto) {
		moto.setUsuarioId(usuarioId);
		Moto nuevoMoto = motoFeingClient.save(moto);
		return nuevoMoto;
	}
	
	public Map<String, Object> getUsuarioAndVeiculos(int usuariId){
		Map<String, Object> resultado = new HashMap<>();
		Usuario usuario = usuarioRepository.findById(usuariId).orElse(null);
		
		if(usuario ==  null){
			resultado.put("Mensaje", "El usuario no exixte");
			return resultado;
		}
		
		resultado.put("Usuario", usuario);
		List<Carro> carros = carroFeignClient.getCarros(usuariId);
		if(carros.isEmpty()){
			resultado.put("Carros", "El usuario no tiene carros");
		}else {
			resultado.put("Carros",carros);
		}
		
		List<Moto> motos = motoFeingClient.getMotos(usuariId);
		if(motos.isEmpty()){
			resultado.put("Motos", "El usuario no tiene motos");
		}else {
			resultado.put("Motos",motos);
		}
		
		return resultado;
	}
}
