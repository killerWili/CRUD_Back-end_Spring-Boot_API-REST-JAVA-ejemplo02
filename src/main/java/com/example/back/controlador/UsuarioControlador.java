package com.example.back.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.example.back.excepciones.ResourceNotFoundException;
import com.example.back.modelo.Usuario;
import com.example.back.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioControlador {
	@Autowired
	private UsuarioRepositorio repositorio;
	
	//metodo para listar todos los usuarios
	@GetMapping("/usuarios")
	public List<Usuario> listarTodosLosUsuarios() {
		return repositorio.findAll();
	}
	//metodo para guardar el usuario
	@PostMapping("/usuarios")
	public Usuario guardarUsuario(@RequestBody Usuario usuario) {
		return repositorio.save(usuario);
	}
	//metodo para buscar un usuario
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> obtenerUsuariosPorId(@PathVariable Long id){
		Usuario usuario = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con el ID : " + id));
		return ResponseEntity.ok(usuario);
	}
	//metodo para actualizar empleado
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario detallesUsuario){
		Usuario usuario = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con el ID : " + id));
		
		usuario.setNombre(detallesUsuario.getNombre());
		usuario.setApellido(detallesUsuario.getApellido());
		usuario.setGenero(detallesUsuario.getGenero());
		
		Usuario usuarioActualizado = repositorio.save(usuario);
		return ResponseEntity.ok(usuarioActualizado);
    }
	//metodo para eliminar un usuario
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<Map<String,Boolean>> eliminarUsuarios(@PathVariable Long id){
		Usuario usuario = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con el ID : " + id));
		
		repositorio.delete(usuario);
		Map<String, Boolean> respuesta = new HashMap<>();
		respuesta.put("eliminar",Boolean.TRUE);
		return ResponseEntity.ok(respuesta);
    }
}
