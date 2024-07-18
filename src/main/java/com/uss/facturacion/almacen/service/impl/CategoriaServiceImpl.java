package com.uss.facturacion.almacen.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uss.facturacion.almacen.entity.Categoria;
import com.uss.facturacion.almacen.exception.GeneralServiceException;
import com.uss.facturacion.almacen.exception.NoDataServiceException;
import com.uss.facturacion.almacen.exception.ValidateServiceException;
import com.uss.facturacion.almacen.repository.CategoriaRepository;
import com.uss.facturacion.almacen.service.CategoriaService;
import com.uss.facturacion.almacen.validator.CategoriaValidator;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	@Autowired
	private CategoriaRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Categoria> findAll() {
		try {
			return repository.findAll();		
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria findById(int id) {
		try {
	        Categoria categoriaDb = repository.findById(id)
	                .orElseThrow(() -> new ValidateServiceException("No hay un registro con ese ID"));
	        
	        return categoriaDb;
	        
	    } catch (ValidateServiceException e) {
	        throw e; 	                 
	    } catch (Exception e) {
	        throw new GeneralServiceException("Error en el servidor");
	    }
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria findByNombre(String nombre) {
		try {
			return repository.findByNombre(nombre);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Categoria> findByNombreContaining(String nombre) {
		try {
			return repository.findByNombreContaining(nombre);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Categoria create(Categoria obj) {
		try {
			CategoriaValidator.save(obj);
			Categoria categoria=findByNombre(obj.getNombre());
			if(categoria!=null) {
				throw new ValidateServiceException("Ya hay un registro con ese nombre");
			}
			obj.setActivo(true);
			return repository.save(obj);			
		} catch (ValidateServiceException e) {
			throw new ValidateServiceException(e.getMessage());
		} catch (Exception e) {
			throw new GeneralServiceException("Error en el servidor");
		}	
	}

	@Override
	@Transactional
	public Categoria update(Categoria obj) {
		try {
			CategoriaValidator.save(obj);
			Categoria categoriaDb=findById(obj.getId());
			//Validamos si ya existe el registro con ese nombre
			Categoria categoria=findByNombre(obj.getNombre());
			if(categoria!=null && obj.getId()!=categoria.getId()) {
				throw new ValidateServiceException("Ya hay un registro con ese nombre");
			}
			categoriaDb.setNombre(obj.getNombre());			
			return repository.save(categoriaDb);
			
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
			Categoria categoriaDb= findById(id);
			if(categoriaDb==null) {
				return 0;
			}else {
				repository.delete(categoriaDb);
				return 1;
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
