package com.uss.facturacion.almacen.validator;

import com.uss.facturacion.almacen.entity.Categoria;
import com.uss.facturacion.almacen.exception.ValidateServiceException;

public class CategoriaValidator {
	public static void save (Categoria categoria) {
		if(categoria.getNombre()==null || categoria.getNombre().trim().isEmpty()) {
			throw new ValidateServiceException("El nombre es requerido");
		}
		if(categoria.getNombre().length()>100) {
			throw new ValidateServiceException("El nombre es muy extenso");
		}
	}

}
