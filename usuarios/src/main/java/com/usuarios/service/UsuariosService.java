package com.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.usuarios.entity.Usuarios;
import com.usuarios.repository.UsuariosRepository;

public class UsuariosService {

	@Autowired
	private UsuariosRepository usuariosRepository;
	
	public List<Usuarios> getAll(){
		return usuariosRepository.findAll();
	}
	
	public Usuarios getUsuariosById(int id) {
		return usuariosRepository.findById(id).orElse(null);
	}
	
	public Usuarios saveUsuarios(Usuarios usuarios) {
		Usuarios nuevoUsuario = usuariosRepository.save(usuarios);
		return nuevoUsuario;
	}
	
	public Usuarios updateUsuariosById(Usuarios request, Integer id){
		Usuarios nuevoUsuario = usuariosRepository.findById(id).get();
        nuevoUsuario.setNombre(request.getNombre());
        nuevoUsuario.setApellido(request.getApellido());
        nuevoUsuario.setEmail(request.getEmail());
        nuevoUsuario.setFechaRegistro(request.getFechaRegistro());
        return nuevoUsuario;
    }
	
	public boolean deleteUsuarios(Integer id){
        try {
         usuariosRepository.deleteById(id);
         return true;
        }catch (Exception e){
            return false;
        }
    }
}
