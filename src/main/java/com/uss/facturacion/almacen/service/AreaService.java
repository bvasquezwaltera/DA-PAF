package com.uss.facturacion.almacen.service;

import java.util.List;

import com.uss.facturacion.almacen.entity.Area;

public interface AreaService {
	public List<Area> findAll();
	public Area findById(int id);
	public Area findByNombre(String nombre);
	public List<Area> findByNombreContaining(String nombre);
    public Area create(Area obj);
    public Area update(Area obj);
    public int delete(int id);
}
