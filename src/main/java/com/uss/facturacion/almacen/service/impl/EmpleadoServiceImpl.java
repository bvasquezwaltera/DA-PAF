package com.uss.facturacion.almacen.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uss.facturacion.almacen.entity.Empleado;
import com.uss.facturacion.almacen.entity.Area;
import com.uss.facturacion.almacen.exception.GeneralServiceException;
import com.uss.facturacion.almacen.exception.NoDataServiceException;
import com.uss.facturacion.almacen.exception.ValidateServiceException;
import com.uss.facturacion.almacen.repository.EmpleadoRepository;
import com.uss.facturacion.almacen.repository.AreaRepository;
import com.uss.facturacion.almacen.service.EmpleadoService;
import com.uss.facturacion.almacen.validator.EmpleadoValidator;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
	@Autowired
	private EmpleadoRepository repository;
	
	@Autowired
    private AreaRepository areaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findAll() {
		try {
			return repository.findAll();		
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Empleado findById(int id) {
		try {
	        Empleado empleadoDb = repository.findById(id)
	                .orElseThrow(() -> new ValidateServiceException("No hay un registro con ese ID"));
	        
	        return empleadoDb;
	        
	    } catch (ValidateServiceException e) {
	        throw e; 	                 
	    } catch (Exception e) {
	        throw new GeneralServiceException("Error en el servidor");
	    }
	}

	@Override
	@Transactional(readOnly = true)
	public Empleado findByNombre(String nombre) {
		try {
			return repository.findByNombre(nombre);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findByNombreContaining(String nombre) {
		try {
			return repository.findByNombreContaining(nombre);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Empleado create(Empleado obj) {
		//try {
			EmpleadoValidator.save(obj);
			Empleado empleado=findByNombre(obj.getNombre());
			if(empleado!=null) {
				throw new ValidateServiceException("Ya hay un registro con ese nombre");
			}
			Area area = areaRepository.findById(obj.getArea().getId())
                    .orElseThrow(() -> new ValidateServiceException("Área no encontrada"));
            obj.setArea(area);
			return repository.save(obj);			
		//} catch (ValidateServiceException e) {
			//throw new ValidateServiceException(e.getMessage());
		//} catch (Exception e) {
			//throw new GeneralServiceException("Error en el servidor");
		//}	
	}

	@Override
	@Transactional
	public Empleado update(Empleado obj) {
		try {
			EmpleadoValidator.save(obj);
			Empleado empleadoDb=findById(obj.getId());
			//Validamos si ya existe el registro con ese nombre
			Empleado empleado=findByNombre(obj.getNombre());
			if(empleado!=null && obj.getId()!=empleado.getId()) {
				throw new ValidateServiceException("Ya hay un registro con ese nombre");
			}
			
			Area area = areaRepository.findById(obj.getArea().getId())
                    .orElseThrow(() -> new ValidateServiceException("Área no encontrada"));
            empleadoDb.setArea(area);
			empleadoDb.setNombre(obj.getNombre());	
			empleadoDb.setDocumento(obj.getDocumento());	
			empleadoDb.setTelefono(obj.getTelefono());	
			empleadoDb.setDireccion(obj.getDireccion());	
			empleadoDb.setEmail(obj.getEmail());	
			
			
			return repository.save(empleadoDb);
			
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
			Empleado empleadoDb= findById(id);
			if(empleadoDb==null) {
				return 0;
			}else {
				repository.delete(empleadoDb);
				return 1;
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
