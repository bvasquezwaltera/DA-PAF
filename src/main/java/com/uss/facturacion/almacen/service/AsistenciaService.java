package com.uss.facturacion.almacen.service;

import java.util.List;

import com.uss.facturacion.almacen.entity.Asistencia;

public interface AsistenciaService {
	public List<Asistencia> findAll();
	public Asistencia findById(int id);
	public List<Asistencia> findAllWithEmpleado();
	public Asistencia findByIdWithEmpleado(int id);
	//public Asistencia findByFicha(String ficha);
	//public List<Asistencia> findByFichaContaining(String ficha);
    public Asistencia create(Asistencia obj);
    public Asistencia update(Asistencia obj);
    public int delete(int id);
}
