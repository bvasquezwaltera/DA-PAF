package com.uss.facturacion.almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uss.facturacion.almacen.entity.Permiso;
import com.uss.facturacion.almacen.service.PermisoService;
import java.util.List;

@RestController
@RequestMapping("/api/permisos") //www.localhost:8081/api/permisos
public class PermisoController {
	@Autowired
	private PermisoService service;
	
	@GetMapping()
	public ResponseEntity<List<Permiso>> getAll(){
		List<Permiso> permisos= service.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(permisos);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Permiso> getById(@PathVariable("id") int id) {
		Permiso permiso = service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(permiso);
	}
	
	@PostMapping
	public ResponseEntity<Permiso> create(@RequestBody Permiso permiso) {
		Permiso permisoDb=service.create(permiso);
		return ResponseEntity.status(HttpStatus.CREATED).body(permisoDb);
	}
	
	@PutMapping
	public ResponseEntity<Permiso> update(@RequestBody Permiso permiso) {
		Permiso permisoDb=service.update(permiso);
		return ResponseEntity.status(HttpStatus.OK).body(permisoDb);
	}
	
	@DeleteMapping(value="/{id}")
	public int delete(@PathVariable("id") int id){
		return service.delete(id);
	}
}
