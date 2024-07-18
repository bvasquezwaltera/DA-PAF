package com.uss.facturacion.almacen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uss.facturacion.almacen.entity.Cliente;
import com.uss.facturacion.almacen.exception.GeneralServiceException;
import com.uss.facturacion.almacen.exception.ValidateServiceException;
import com.uss.facturacion.almacen.repository.ClienteRepository;
import com.uss.facturacion.almacen.service.ClienteService;
import com.uss.facturacion.almacen.validator.ClienteValidator;


@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		try {
			return repository.findAll();		
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(int id) {
		try {
	        Cliente clienteDb = repository.findById(id)
	                .orElseThrow(() -> new ValidateServiceException("No hay un registro con ese ID"));
	        
	        return clienteDb;
	        
	    } catch (ValidateServiceException e) {
	        throw e; 	                 
	    } catch (Exception e) {
	        throw new GeneralServiceException("Error en el servidor");
	    }
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findByNombre(String nombre) {
		try {
			return repository.findByNombre(nombre);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findByNombreContaining(String nombre) {
		try {
			return repository.findByNombreContaining(nombre);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Cliente create(Cliente obj) {
		try {
			ClienteValidator.save(obj);
			Cliente cliente=findByNombre(obj.getNombre());
			if(cliente!=null) {
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
	public Cliente update(Cliente obj) {
		try {
			ClienteValidator.save(obj);
			Cliente clienteDb=findById(obj.getId());
			//Validamos si ya existe el registro con ese nombre
			Cliente cliente=findByNombre(obj.getNombre());
			if(cliente!=null && obj.getId()!=cliente.getId()) {
				throw new ValidateServiceException("Ya hay un registro con ese nombre");
			}
			clienteDb.setNombre(obj.getNombre());			
			clienteDb.setDocumento(obj.getDocumento());			
			clienteDb.setTelefono(obj.getTelefono());			
			clienteDb.setDireccion(obj.getDireccion());			
			clienteDb.setEmail(obj.getEmail());			
			return repository.save(clienteDb);
			
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
			Cliente clienteDb= findById(id);
			if(clienteDb==null) {
				return 0;
			}else {
				repository.delete(clienteDb);
				return 1;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	
}
