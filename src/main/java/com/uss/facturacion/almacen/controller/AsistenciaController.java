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

import com.uss.facturacion.almacen.entity.Asistencia;
import com.uss.facturacion.almacen.service.AsistenciaService;
import java.util.List;

@RestController
@RequestMapping("/api/asistencias") //www.localhost:8081/api/asistencias
public class AsistenciaController {
	@Autowired
	private AsistenciaService service;
	
	@GetMapping()
	public ResponseEntity<List<Asistencia>> getAll(){
		List<Asistencia> asistencias= service.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(asistencias);
	}
	
	/*@GetMapping()
	public ResponseEntity<List<Asistencia>> getAll(){
	    List<Asistencia> asistencias = service.findAll();
	    // Logging para verificar los datos antes de enviar la respuesta
	    System.out.println("Asistencias encontradas: " + asistencias.size());
	    for (Asistencia asistencia : asistencias) {
	        System.out.println("Asistencia ID: " + asistencia.getId());
	        if (asistencia.getEmpleado() != null) {
	            System.out.println("Empleado ID: " + asistencia.getEmpleado().getId());
	            System.out.println("Empleado Nombre: " + asistencia.getEmpleado().getNombre());
	        }
	    }
	    return ResponseEntity.status(HttpStatus.OK).body(asistencias);
	}*/
	
	/*@GetMapping()
    public ResponseEntity<List<Asistencia>> getAll() {
        List<Asistencia> asistencias = service.findAllWithEmpleado();
        // Logging para verificar los datos antes de enviar la respuesta
        System.out.println("Asistencias encontradas: " + asistencias.size());
        for (Asistencia asistencia : asistencias) {
            System.out.println("Asistencia ID: " + asistencia.getId());
            if (asistencia.getEmpleado() != null) {
                System.out.println("Empleado ID: " + asistencia.getEmpleado().getId());
                System.out.println("Empleado Nombre: " + asistencia.getEmpleado().getNombre());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(asistencias);
    }*/

	
	@GetMapping(value="/{id}")
	public ResponseEntity<Asistencia> getById(@PathVariable("id") int id) {
		Asistencia asistencia = service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(asistencia);
	}
	
	@PostMapping
	public ResponseEntity<Asistencia> create(@RequestBody Asistencia asistencia) {
		Asistencia asistenciaDb=service.create(asistencia);
		return ResponseEntity.status(HttpStatus.CREATED).body(asistenciaDb);
	}
	
	@PutMapping
	public ResponseEntity<Asistencia> update(@RequestBody Asistencia asistencia) {
		Asistencia asistenciaDb=service.update(asistencia);
		return ResponseEntity.status(HttpStatus.OK).body(asistenciaDb);
	}
	
	@DeleteMapping(value="/{id}")
	public int delete(@PathVariable("id") int id){
		return service.delete(id);
	}
}
