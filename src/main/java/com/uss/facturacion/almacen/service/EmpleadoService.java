package com.uss.facturacion.almacen.service;

import java.util.List;

import com.uss.facturacion.almacen.entity.Empleado;

public interface EmpleadoService {
	public List<Empleado> findAll();
	public Empleado findById(int id);
	public Empleado findByNombre(String nombre);
	public List<Empleado> findByNombreContaining(String nombre);
    public Empleado create(Empleado obj);
    public Empleado update(Empleado obj);
    public int delete(int id);
}
