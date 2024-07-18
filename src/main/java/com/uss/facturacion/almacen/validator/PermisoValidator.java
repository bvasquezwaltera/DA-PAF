package com.uss.facturacion.almacen.validator;

import com.uss.facturacion.almacen.entity.Permiso;
import com.uss.facturacion.almacen.exception.ValidateServiceException;

public class PermisoValidator {
	public static void save (Permiso empleado) {
		if(empleado.getFicha()==null || empleado.getFicha().trim().isEmpty()) {
			throw new ValidateServiceException("La ficha del permiso es requerido");
		}
		if(empleado.getMotivo().length()>100) {
			throw new ValidateServiceException("La ficha del permiso es muy extenso");
		}
	}

}
