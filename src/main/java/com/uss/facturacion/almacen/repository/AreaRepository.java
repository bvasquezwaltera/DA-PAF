package com.uss.facturacion.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uss.facturacion.almacen.entity.Area;
import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>{
	public Area findByNombre(String nombre);
	public List<Area> findByNombreContaining (String nombre);	
}
