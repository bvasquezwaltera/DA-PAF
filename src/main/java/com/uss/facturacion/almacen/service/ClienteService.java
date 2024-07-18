package com.uss.facturacion.almacen.service;

import java.util.List;

import com.uss.facturacion.almacen.entity.Cliente;

public interface ClienteService {
	public List<Cliente> findAll();
	public Cliente findById(int id);
	public Cliente findByNombre(String nombre);
	public List<Cliente> findByNombreContaining(String nombre);
    public Cliente create(Cliente obj);
    public Cliente update(Cliente obj);
    public int delete(int id);
}
