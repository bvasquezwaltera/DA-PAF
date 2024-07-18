package com.uss.facturacion.almacen.validator;

import com.uss.facturacion.almacen.entity.Empleado;
import com.uss.facturacion.almacen.exception.ValidateServiceException;

public class EmpleadoValidator {
	public static void save (Empleado empleado) {
		if(empleado.getNombre()==null || empleado.getNombre().trim().isEmpty()) {
			throw new ValidateServiceException("El nombre es requerido");
		}
		if(empleado.getNombre().length()>100) {
			throw new ValidateServiceException("El nombre es muy extenso");
		}
	}

}
