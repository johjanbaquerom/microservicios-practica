package com.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuarios.entity.Usuarios;
import com.usuarios.service.UsuariosService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuariosService usuariosService;
	
	@GetMapping
	public ResponseEntity<List<Usuarios>> listarUsuarios(){
		List<Usuarios> usuarios = usuariosService.getAll();
		if(usuarios.isEmpty()){
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuarios> obtenerUsuarios(@PathVariable("id")int id) {
		Usuarios usuarios = usuariosService.getUsuariosById(id);
		if(usuarios == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuarios);
	}
	
	@PostMapping
	public ResponseEntity<Usuarios> guardarUsuarios(@RequestBody Usuarios usuarios){
		Usuarios nuevoUsuario = usuariosService.saveUsuarios(usuarios);
		return ResponseEntity.ok(nuevoUsuario);
	}

    @PutMapping(path = "/{id}")
    public Usuarios updateUsuariosById(@RequestBody Usuarios request, Integer id){
       return this.usuariosService.updateUsuariosById(request, id);
    }
    
    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        boolean ok = usuariosService.deleteUsuarios(id);

        if(ok){
          return "usuario con id " + id + "¡eliminar!";
        }else {
            return "error tenemos problema " + id + "eliminar";
        }
    }
}
