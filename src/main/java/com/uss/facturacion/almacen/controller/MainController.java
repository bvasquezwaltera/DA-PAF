package com.uss.facturacion.almacen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String getIndex(Model model) {
		return "index";
	}
	@GetMapping("/categorias")
	public String getCategorias(Model model) {
		return "categoria";
	}
	@GetMapping("/clientes")
	public String getClientes(Model model) {
		return "cliente";
	}
	@GetMapping("/areas")
	public String getAreas(Model model) {
		return "area";
	}
	@GetMapping("/empleados")
	public String getEmpleados(Model model) {
		return "empleado";
	}
	@GetMapping("/permisos")
	public String getPermisos(Model model) {
		return "permiso";
	}
	@GetMapping("/asistencias")
	public String getAsistencias(Model model) {
		return "asistencia";
	}
}
