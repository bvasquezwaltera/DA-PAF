package com.uss.facturacion.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uss.facturacion.almacen.entity.Permiso;
import java.util.List;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer>{
	public Permiso findByFicha(String ficha);
	public List<Permiso> findByFichaContaining (String ficha);	
}
