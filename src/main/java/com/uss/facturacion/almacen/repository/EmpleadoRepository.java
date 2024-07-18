package com.uss.facturacion.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uss.facturacion.almacen.entity.Empleado;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{
	public Empleado findByNombre(String nombre);
	public List<Empleado> findByNombreContaining (String nombre);	
}
