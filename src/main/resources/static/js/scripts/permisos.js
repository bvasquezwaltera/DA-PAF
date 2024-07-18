const url = "api/permisos";
const urlEmpleados = "api/empleados";

function save(bandera) {		
    $("#modal-update").modal("hide")
    let id = $("#guardar").data("id");    
    let permiso = {
        id: id,
        ficha : $("#ficha").val(),
        fechaInicio : $("#fechaInicio").val(),
        fechaFin: $("#fechaFin").val(),
        motivo : $("#motivo").val(),//motivo
		empleado: {
		            id: $("#empleado").val() // Aquí capturas el ID del empleado seleccionado
		        }
				
    }
	
	//console.log("Datos de permiso a guardar:", permiso); // Añadir esto para ver los datos en la consola

	
    let metodo = (bandera == 1) ? "POST" : "PUT";
    $.ajax({
        type: metodo,
        url: url,
        data: JSON.stringify(permiso),
        dataType: "json",
        contentType: "application/json",
        cache: false,
        success: function (data) {
			if(data==0){
				Swal.fire({
	                icon: 'error',
	                title: 'El permiso ya esta registrado',
	                showConfirmButton: false,
	                timer: 1500
	            })				
			}else{
	            let texto = bandera == 1 ? "guardado": "actualizado";
	            getTabla();
	            Swal.fire({
	                icon: 'success',
	                title: 'Se ha '+texto+' el permiso',
	                showConfirmButton: false,
	                timer: 1500
	            })
	            clear();
            }
        },
    }).fail(function () {
		console.error("Error en la solicitud AJAX");
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
                title: 'Se ha eliminado el permiso',
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
            let t = $("#tablaPermisos").DataTable();
            t.clear().draw(false);
			
			//let permisos = data.filter(item => typeof item === 'object'); 

            $.each(data, function (index, permiso) {
                let empleadoNombre = permiso.empleado ? permiso.empleado.nombre : ''; // Verifica si permiso.empleado es definido

                let botonera = '<button type="button" class="btn btn-warning btn-sm editar"><i class="fas fa-edit"></i> </button>' +
                    '<button type="button" class="btn btn-danger btn-sm eliminar"><i class="fas fa-trash"></i></button>';
				
					//console.log(permiso);
					
                t.row.add([
                    permiso.id,
                    permiso.ficha,
                    permiso.fechaInicio,
                    permiso.fechaFin,
                    permiso.motivo,
                    empleadoNombre, // Utiliza empleadoNombre en lugar de permiso.empleado.nombre directamente
                    botonera
                ]);
            });

            t.draw(false);

        },
        error: function () {
            // Manejo de errores
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
            $("#modal-title").text("Editar Permiso");
            $("#ficha").val(data.ficha);
            $("#fechaInicio").val(data.fechaInicio);
            $("#fechaFin").val(data.fechaFin);
            $("#motivo").val(data.motivo);
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
    $("#modal-title").text("Nuevo Permiso");
    $("#observacion").val("");
    $("#fechaHoraEntrada").val("");
    $("#fechaHoraSalida").val("");
    $("#empleado").val("");
    $("#guardar").data("id", 0);
    $("#guardar").data("bandera", 1);
}

$(document).ready(function () {

    $("#tablaPermisos").DataTable({
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
            { targets: 1, width: "80%" },
            { targets: 2, orderable: false, width: "10%" }
        ],
    });

    clear();

    $("#nuevo").click(function () {
        clear();
		cargarEmpleados();
    });

    $("#guardar").click(function () {
		
		// Validación de fechas
       const fechaInicio = new Date($('#fechaInicio').val());
       const fechaFin = new Date($('#fechaFin').val());

       if (fechaFin <= fechaInicio) {
           Swal.fire({
               icon: 'error',
               title: 'Error en las fechas',
               text: 'La fecha de fin debe ser posterior a la fecha de inicio',
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
            title: 'Eliminar Permiso',
            text: "¿Esta seguro de querer eliminar este permiso?",
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

