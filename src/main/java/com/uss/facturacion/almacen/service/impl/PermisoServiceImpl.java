package com.uss.facturacion.almacen.service.impl;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uss.facturacion.almacen.entity.Permiso;
import com.uss.facturacion.almacen.entity.Empleado;
import com.uss.facturacion.almacen.exception.GeneralServiceException;
import com.uss.facturacion.almacen.exception.NoDataServiceException;
import com.uss.facturacion.almacen.exception.ValidateServiceException;
import com.uss.facturacion.almacen.repository.PermisoRepository;
import com.uss.facturacion.almacen.repository.EmpleadoRepository;
import com.uss.facturacion.almacen.service.PermisoService;
import com.uss.facturacion.almacen.validator.PermisoValidator;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Service
public class PermisoServiceImpl implements PermisoService {
	@Autowired
	private PermisoRepository repository;
	
	@Autowired
    private EmpleadoRepository empleadoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Permiso> findAll() {
		try {
			return repository.findAll();		
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Permiso findById(int id) {
		try {
	        Permiso permisoDb = repository.findById(id)
	                .orElseThrow(() -> new ValidateServiceException("No hay un registro con ese ID"));
	        
	        return permisoDb;
	        
	    } catch (ValidateServiceException e) {
	        throw e; 	                 
	    } catch (Exception e) {
	        throw new GeneralServiceException("Error en el servidor");
	    }
	}

	@Override
	@Transactional(readOnly = true)
	public Permiso findByFicha(String ficha) {
		try {
			return repository.findByFicha(ficha);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Permiso> findByFichaContaining(String ficha) {
		try {
			return repository.findByFichaContaining(ficha);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Permiso create(Permiso obj) {
		try {
			PermisoValidator.save(obj);
			Permiso permiso=findByFicha(obj.getFicha());
			if(permiso!=null) {
				throw new ValidateServiceException("Ya hay un registro con ese ficha");
			}
			Empleado empleado = empleadoRepository.findById(obj.getEmpleado().getId())
                    .orElseThrow(() -> new ValidateServiceException("Permiso no encontrado"));
            obj.setEmpleado(empleado);
			return repository.save(obj);			
		} catch (ValidateServiceException e) {
			throw new ValidateServiceException(e.getMessage());
		} catch (Exception e) {
			throw new GeneralServiceException("Error en el servidor");
		}	
	}

	@Override
	@Transactional
	public Permiso update(Permiso obj) {
		try {
			PermisoValidator.save(obj);
			Permiso permisoDb=findById(obj.getId());
			//Validamos si ya existe el registro con ese nombre
			Permiso permiso=findByFicha(obj.getFicha());
			if(permiso!=null && obj.getId()!=permiso.getId()) {
				throw new ValidateServiceException("Ya hay un registro con ese ficha");
			}
			
			Empleado empleado = empleadoRepository.findById(obj.getEmpleado().getId())
                    .orElseThrow(() -> new ValidateServiceException("Empleado no encontrado"));
            permisoDb.setEmpleado(empleado);
			permisoDb.setFicha(obj.getFicha());	
			permisoDb.setFechaInicio(obj.getFechaInicio());	
			permisoDb.setFechaFin(obj.getFechaFin());	
			permisoDb.setMotivo(obj.getMotivo());	
			
			return repository.save(permisoDb);
			
		} catch (ValidateServiceException e) {
			throw new ValidateServiceException(e.getMessage());
		} catch (Exception e) {
			throw new GeneralServiceException("Error en el servidor");
		}
	}

	@Override
	@Transactional
	public int delete(int id) {
		try {
			Permiso permisoDb= findById(id);
			if(permisoDb==null) {
				return 0;
			}else {
				repository.delete(permisoDb);
				return 1;
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
