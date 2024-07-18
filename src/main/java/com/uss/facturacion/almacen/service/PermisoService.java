package com.uss.facturacion.almacen.service;

import java.util.List;

import com.uss.facturacion.almacen.entity.Permiso;

public interface PermisoService {
	public List<Permiso> findAll();
	public Permiso findById(int id);
	public Permiso findByFicha(String ficha);
	public List<Permiso> findByFichaContaining(String ficha);
    public Permiso create(Permiso obj);
    public Permiso update(Permiso obj);
    public int delete(int id);
}
