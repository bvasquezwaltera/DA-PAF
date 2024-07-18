const url = "api/asistencias";
const urlEmpleados = "api/empleados";


function save(bandera) {		
    $("#modal-update").modal("hide")
    let id = $("#guardar").data("id");    
    let asistencia = {
        id: id,
        observacion: $("#observacion").val(),
        fechaHoraEntrada : $("#fechaHoraEntrada").val(),
        fechaHoraSalida: $("#fechaHoraSalida").val(),
		empleado: {
		            id: $("#empleado").val() // Aquí capturas el ID del empleado seleccionado
		        }
				
    }
    let metodo = (bandera == 1) ? "POST" : "PUT";
    $.ajax({
        type: metodo,
        url: url,
        data: JSON.stringify(asistencia),
        dataType: "json",
        contentType: "application/json",
        cache: false,
        success: function (data) {
			if(data==0){
				Swal.fire({
	                icon: 'error',
	                title: 'El asistencia ya esta registrado',
	                showConfirmButton: false,
	                timer: 1500
	            })				
			}else{
	            let texto = bandera == 1 ? "guardado": "actualizado";
	            getTabla();
	            Swal.fire({
	                icon: 'success',
	                title: 'Se ha '+texto+' el asistencia',
	                showConfirmButton: false,
	                timer: 1500
	            })
	            clear();
            }
        },
    }).fail(function () {
        
    });
}

function deleteFila(id) {
    $.ajax({
        type: "DELETE",
        url: url + "/"+id,
        data: {
            id: id,
        },
        cache: false,
        timeout: 600000,
        success: function (data) {
            Swal.fire({
                icon: 'success',
                title: 'Se ha eliminado el asistencia',
                showConfirmButton: false,
                timer: 1500
            });
            getTabla();
        },
    }).fail(function () {

    });

}


function getTabla() {
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        cache: false,
        success: function (data) {
			//console.log(data);
            let t = $("#tablaAsistencias").DataTable();
            //t.clear().draw(false);
            t.clear();
			
			let asistencias = data.filter(item => typeof item === 'object');//asegurarse de que solo se procesen los elementos del array data que son objetos

            $.each(asistencias, function (index, asistencia) {
                let empleadoNombre = asistencia.empleado ? asistencia.empleado.nombre : ''; // Verifica si asistencia.empleado es definido

                let botonera = '<button type="button" class="btn btn-warning btn-sm editar"><i class="fas fa-edit"></i> </button>' +
                    '<button type="button" class="btn btn-danger btn-sm eliminar"><i class="fas fa-trash"></i></button>';
				
					//console.log(asistencia);
					
                t.row.add([
                    asistencia.id,
					empleadoNombre, // Utiliza empleadoNombre en lugar de asistencia.empleado.nombre directamente
                    asistencia.observacion,
                    asistencia.fechaHoraEntrada,
                    asistencia.fechaHoraSalida,                  
                    botonera
                ]).draw(false);
            });

            t.draw(false);

        },
        error: function () {
            console.error("Error al obtener las asistencias.");
        }
    });
}



function getFila(id) {

    $.ajax({
        type: "GET",
        url: url + "/"+id,
        data: {
            id: id,
        },
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#modal-title").text("Editar Asistencia");
            $("#observacion").val(data.observacion);
            $("#fechaHoraEntrada").val(data.fechaHoraEntrada);
            $("#fechaHoraSalida").val(data.fechaHoraSalida);
            $("#empleado").val(data.empleado);    
            $("#guardar").data("id", data.id);
            $("#guardar").data("bandera", 0);
            $("#modal-update").modal("show");
        },
    }).fail(function () {

    });
}



function cargarEmpleados() {
    $.ajax({
        type: "GET",
        url: urlEmpleados,
        dataType: "json", // Esperamos recibir datos en formato JSON
        success: function (data) {
            let options = $("#empleado"); // Referencia al elemento <select>
            options.empty(); // Limpiar todas las opciones existentes

            // Agregar la opción inicial
            options.append($("<option>").val("").text("Seleccione un empleado"));

            // Recorrer cada área recibida y agregarla como una opción en el <select>
            $.each(data, function (key, empleado) {
                options.append($("<option>").val(empleado.id).text(empleado.nombre));
            });
        },
        error: function () {
            // Manejar errores en caso de que falle la solicitud AJAX
            console.error("Error al cargar los empleados.");
        }
    });
}



function clear() {
    $("#modal-title").text("Nuevo Asistencia");
    $("#ficha").val("");
    $("#fechaInicio").val("");
    $("#fechaFin").val("");
    $("#motivo").val("");
    $("#empleado").val("");
    $("#guardar").data("id", 0);
    $("#guardar").data("bandera", 1);
}

$(document).ready(function () {

    $("#tablaAsistencias").DataTable({
        language: {
            lengthMenu: "Mostrar _MENU_ registros",
            zeroRecords: "No se encontraron coincidencias",
            info: "Mostrando del _START_ al _END_ DE _TOTAL_",
            infoEmpty: "Sin resultados",
            search: "Buscar: ",
            paginate: {
                first: "Primero",
                last: "Último",
                next: "Siguiente",
                previous: "Anterior",
            },
        },
        columnDefs: [
            { targets: 0, width: "10%" },
            { targets: 1, width: "40%" },
            { targets: 2, orderable: false, width: "50%" }
        ],
    });

    clear();

    $("#nuevo").click(function () {
        clear();
		cargarEmpleados();
    });

    $("#guardar").click(function () {
		
		// Validación de fechas
       const fechaHoraEntrada = new Date($('#fechaHoraEntrada').val());
       const fechaHoraSalida= new Date($('#fechaHoraSalida').val());

       if (fechaHoraSalida <= fechaHoraEntrada) {
           Swal.fire({
               icon: 'error',
               title: 'Error en las fechas',
               text: 'La fecha de hora salida debe ser posterior a la fecha de hora entrada',
               showConfirmButton: false,
               timer: 1500
           });
           return;
       }

        let bandera = $("#guardar").data("bandera");
        save(bandera);
    })

    $(document).on('click', '.eliminar', function () {
        Swal.fire({
            title: 'Eliminar Asistencia',
            text: "¿Esta seguro de querer eliminar este asistencia?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Si'
        }).then((result) => {
            if (result.isConfirmed) {
                let id = $($(this).parents('tr')[0].children[0]).text();
                deleteFila(id);
            }
        })
    });

    $(document).on('click', '.editar', function () {
        let id = $($(this).parents('tr')[0].children[0]).text();
        getFila(id);
    });
    getTabla();
});

