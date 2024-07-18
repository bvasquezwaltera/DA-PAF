package com.uss.facturacion.almacen.service.impl;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uss.facturacion.almacen.entity.Asistencia;
import com.uss.facturacion.almacen.entity.Empleado;
import com.uss.facturacion.almacen.exception.GeneralServiceException;
import com.uss.facturacion.almacen.exception.NoDataServiceException;
import com.uss.facturacion.almacen.exception.ValidateServiceException;
import com.uss.facturacion.almacen.repository.AsistenciaRepository;
import com.uss.facturacion.almacen.repository.EmpleadoRepository;
import com.uss.facturacion.almacen.service.AsistenciaService;
import com.uss.facturacion.almacen.validator.AsistenciaValidator;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {
	@Autowired
	private AsistenciaRepository repository;
	
	@Autowired
    private EmpleadoRepository empleadoRepository;
	
	@Override
    public Asistencia findByIdWithEmpleado(int id) {
        return repository.findByIdWithEmpleado(id);
    }
	
	
	public List<Asistencia> findAllWithEmpleado() {
        return repository.findAllWithEmpleado();
    }
	
	@Override
	@Transactional(readOnly = true)
	public List<Asistencia> findAll() {
		try {
			return repository.findAll();		
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Asistencia findById(int id) {
		try {
	        Asistencia asistenciaDb = repository.findById(id)
	                .orElseThrow(() -> new ValidateServiceException("No hay un registro con ese ID"));
	        
	        return asistenciaDb;
	        
	    } catch (ValidateServiceException e) {
	        throw e; 	                 
	    } catch (Exception e) {
	        throw new GeneralServiceException("Error en el servidor");
	    }
	}

	//@Override
	//@Transactional(readOnly = true)
	//public Asistencia findByFicha(String ficha) {
		//try {
			//return repository.findByFicha(ficha);
		//} catch (Exception e) {
		//	throw e;
	//	}
	//}

	/*@Override
	@Transactional(readOnly = true)
	public List<Asistencia> findByFichaContaining(String ficha) {
		try {
			return repository.findByFichaContaining(ficha);
		} catch (Exception e) {
			throw e;
		}
	}*/

	@Override
	@Transactional
	public Asistencia create(Asistencia obj) {
		try {
			AsistenciaValidator.save(obj);
			//Asistencia asistencia=findByFicha(obj.getFicha());
			//if(asistencia!=null) {
				//throw new ValidateServiceException("Ya hay un registro con ese ficha");
			//}
			Empleado empleado = empleadoRepository.findById(obj.getEmpleado().getId())
                    .orElseThrow(() -> new ValidateServiceException("Empleado no encontrado"));
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
	public Asistencia update(Asistencia obj) {
		try {
			AsistenciaValidator.save(obj);
			Asistencia asistenciaDb=findById(obj.getId());
			//Validamos si ya existe el registro con ese nombre
			//Asistencia asistencia=findByFicha(obj.getFicha());
			//if(asistencia!=null && obj.getId()!=asistencia.getId()) {
				//throw new ValidateServiceException("Ya hay un registro con ese ficha");
			//}
			
			Empleado empleado = empleadoRepository.findById(obj.getEmpleado().getId())
                    .orElseThrow(() -> new ValidateServiceException("Empleado no encontrado"));
            asistenciaDb.setEmpleado(empleado);
			asistenciaDb.setObservacion(obj.getObservacion());	
			asistenciaDb.setFechaHoraEntrada(obj.getFechaHoraEntrada());	
			asistenciaDb.setFechaHoraSalida(obj.getFechaHoraSalida());	

			return repository.save(asistenciaDb);
			
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
			Asistencia asistenciaDb= findById(id);
			if(asistenciaDb==null) {
				return 0;
			}else {
				repository.delete(asistenciaDb);
				return 1;
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
