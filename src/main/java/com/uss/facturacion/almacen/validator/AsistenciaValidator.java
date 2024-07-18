package com.uss.facturacion.almacen.validator;

import com.uss.facturacion.almacen.entity.Asistencia;
import com.uss.facturacion.almacen.exception.ValidateServiceException;

public class AsistenciaValidator {
	public static void save (Asistencia asistencia) {

		if(asistencia.getObservacion().length()>100) {
			throw new ValidateServiceException("La observacion de la asistencia es muy extenso");
		}
	}

}
