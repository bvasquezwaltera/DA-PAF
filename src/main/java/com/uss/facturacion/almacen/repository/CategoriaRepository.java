package com.uss.facturacion.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uss.facturacion.almacen.entity.Categoria;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	public Categoria findByNombre(String nombre);
	public List<Categoria> findByNombreContaining (String nombre);	
}
