package com.uss.facturacion.almacen.validator;

import com.uss.facturacion.almacen.entity.Area;
import com.uss.facturacion.almacen.exception.ValidateServiceException;

public class AreaValidator {
	public static void save (Area area) {
		if(area.getNombre()==null || area.getNombre().trim().isEmpty()) {
			throw new ValidateServiceException("El nombre es requerido");
		}
		if(area.getNombre().length()>100) {
			throw new ValidateServiceException("El nombre es muy extenso");
		}
	}

}
