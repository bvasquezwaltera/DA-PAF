package com.uss.facturacion.almacen.validator;


import com.uss.facturacion.almacen.entity.Cliente;
import com.uss.facturacion.almacen.exception.ValidateServiceException;

public class ClienteValidator {
	public static void save (Cliente cliente) {
		if(cliente.getNombre()==null || cliente.getNombre().trim().isEmpty()) {
			throw new ValidateServiceException("El nombre es requerido");
		}
		if(cliente.getNombre().length()>100) {
			throw new ValidateServiceException("El nombre es muy extenso");
		}
	}
}
