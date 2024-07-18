package com.uss.facturacion.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uss.facturacion.almacen.entity.Asistencia;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer>{
	//public Asistencia findByFicha(String ficha);
	//public List<Asistencia> findByFichaContaining (String ficha);	
	@Query("SELECT a FROM Asistencia a JOIN FETCH a.empleado WHERE a.id = :id")
    Asistencia findByIdWithEmpleado(@Param("id") int id);
	
	@Query("SELECT a FROM Asistencia a JOIN FETCH a.empleado")
    List<Asistencia> findAllWithEmpleado();
}
	