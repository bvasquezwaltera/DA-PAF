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

import com.uss.facturacion.almacen.entity.Categoria;
import com.uss.facturacion.almacen.service.CategoriaService;
import java.util.List;

@RestController
@RequestMapping("/api/categorias") //www.localhost:8081/api/categorias
public class CategoriaController {
	@Autowired
	private CategoriaService service;
	
	@GetMapping()
	public ResponseEntity<List<Categoria>> getAll(){
		List<Categoria> categorias= service.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(categorias);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable("id") int id) {
		Categoria categoria = service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(categoria);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> create(@RequestBody Categoria categoria) {
		Categoria categoriaDb=service.create(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDb);
	}
	
	@PutMapping
	public ResponseEntity<Categoria> update(@RequestBody Categoria categoria) {
		Categoria categoriaDb=service.update(categoria);
		return ResponseEntity.status(HttpStatus.OK).body(categoriaDb);
	}
	
	@DeleteMapping(value="/{id}")
	public int delete(@PathVariable("id") int id){
		return service.delete(id);
	}
}
