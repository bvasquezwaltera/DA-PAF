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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uss.facturacion.almacen.entity.Empleado;
import com.uss.facturacion.almacen.service.EmpleadoService;
import java.util.List;

@RestController
@RequestMapping("/api/empleados") //www.localhost:8081/api/empleados
public class EmpleadoController {
	@Autowired
	private EmpleadoService service;

	@GetMapping()
	public ResponseEntity<List<Empleado>> getAll(){
		List<Empleado> empleados= service.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(empleados);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Empleado> getById(@PathVariable("id") int id) {
		Empleado empleado = service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(empleado);
	}
	
	@PostMapping
	public ResponseEntity<Empleado> create(@RequestBody Empleado empleado) {
		
		Empleado empleadoDb=service.create(empleado);
		return ResponseEntity.status(HttpStatus.CREATED).body(empleadoDb);
	}
	
	@PutMapping
	public ResponseEntity<Empleado> update(@RequestBody Empleado empleado) {
		Empleado empleadoDb=service.update(empleado);
		return ResponseEntity.status(HttpStatus.OK).body(empleadoDb);
	}
	
	@DeleteMapping(value="/{id}")
	public int delete(@PathVariable("id") int id){
		return service.delete(id);
	}
}
