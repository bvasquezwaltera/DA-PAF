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

import com.uss.facturacion.almacen.entity.Area;
import com.uss.facturacion.almacen.service.AreaService;
import java.util.List;

@RestController
@RequestMapping("/api/areas") //www.localhost:8081/api/areas
public class AreaController {
	@Autowired
	private AreaService service;
	
	@GetMapping()
	public ResponseEntity<List<Area>> getAll(){
		List<Area> areas= service.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(areas);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Area> getById(@PathVariable("id") int id) {
		Area area = service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(area);
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Area> create(@RequestBody Area area) {
		Area areaDb=service.create(area);
		return ResponseEntity.status(HttpStatus.CREATED).body(areaDb);
	}
	
	@PutMapping
	public ResponseEntity<Area> update(@RequestBody Area area) {
		Area areaDb=service.update(area);
		return ResponseEntity.status(HttpStatus.OK).body(areaDb);
	}
	
	@DeleteMapping(value="/{id}")
	public int delete(@PathVariable("id") int id){
		return service.delete(id);
	}
}
