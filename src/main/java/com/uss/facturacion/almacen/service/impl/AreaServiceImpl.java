package com.uss.facturacion.almacen.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uss.facturacion.almacen.entity.Area;
import com.uss.facturacion.almacen.exception.GeneralServiceException;
import com.uss.facturacion.almacen.exception.NoDataServiceException;
import com.uss.facturacion.almacen.exception.ValidateServiceException;
import com.uss.facturacion.almacen.repository.AreaRepository;
import com.uss.facturacion.almacen.service.AreaService;
import com.uss.facturacion.almacen.validator.AreaValidator;

@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Area> findAll() {
		try {
			return repository.findAll();		
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Area findById(int id) {
		try {
	        Area areaDb = repository.findById(id)
	                .orElseThrow(() -> new ValidateServiceException("No hay un registro con ese ID"));
	        
	        return areaDb;
	        
	    } catch (ValidateServiceException e) {
	        throw e; 	                 
	    } catch (Exception e) {
	        throw new GeneralServiceException("Error en el servidor");
	    }
	}

	@Override
	@Transactional(readOnly = true)
	public Area findByNombre(String nombre) {
		try {
			return repository.findByNombre(nombre);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findByNombreContaining(String nombre) {
		try {
			return repository.findByNombreContaining(nombre);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Area create(Area obj) {
		try {
			AreaValidator.save(obj);
			Area area=findByNombre(obj.getNombre());
			if(area!=null) {
				throw new ValidateServiceException("Ya hay un registro con ese nombre");
			}
			//obj.setActivo(true);
			return repository.save(obj);			
		} catch (ValidateServiceException e) {
			throw new ValidateServiceException(e.getMessage());
		} catch (Exception e) {
			throw new GeneralServiceException("Error en el servidor");
		}	
	}

	@Override
	@Transactional
	public Area update(Area obj) {
		try {
			AreaValidator.save(obj);
			Area areaDb=findById(obj.getId());
			//Validamos si ya existe el registro con ese nombre
			Area area=findByNombre(obj.getNombre());
			if(area!=null && obj.getId()!=area.getId()) {
				throw new ValidateServiceException("Ya hay un registro con ese nombre");
			}
			areaDb.setNombre(obj.getNombre());	
			areaDb.setDescripcion(obj.getDescripcion());	
			
			return repository.save(areaDb);
			
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
			Area areaDb= findById(id);
			if(areaDb==null) {
				return 0;
			}else {
				repository.delete(areaDb);
				return 1;
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
