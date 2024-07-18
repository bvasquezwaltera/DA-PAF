const url = "api/empleados";
const urlAreas = "api/areas";

function save(bandera) {
    $("#modal-update").modal("hide")
    let id = $("#guardar").data("id");    
    let empleado = {
        id: id,
        nombre : $("#nombre").val(),
        documento : $("#documento").val(),
        telefono : $("#telefono").val(),
        direccion : $("#direccion").val(),
        email: $("#email").val(),
		area: {
		            id: $("#area").val() // Aquí capturas el ID del área seleccionada
		        }
				
    }
    let metodo = (bandera == 1) ? "POST" : "PUT";
    $.ajax({
        type: metodo,
        url: url,
        data: JSON.stringify(empleado),
        dataType: "json",
        contentType: "application/json",
        cache: false,
        success: function (data) {
			if(data==0){
				Swal.fire({
	                icon: 'error',
	                title: 'El empleado ya esta registrado',
	                showConfirmButton: false,
	                timer: 1500
	            })				
			}else{
	            let texto = bandera == 1 ? "guardado": "actualizado";
	            getTabla();
	            Swal.fire({
	                icon: 'success',
	                title: 'Se ha '+texto+' el empleado',
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
                title: 'Se ha eliminado el empleado',
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
            let t = $("#tablaEmpleados").DataTable();
            t.clear().draw(false);
			

            $.each(data, function (index, empleado) {
                let areaNombre = empleado.area ? empleado.area.nombre : ''; // Verifica si empleado.area es definido

                let botonera = '<button type="button" class="btn btn-warning btn-sm editar"><i class="fas fa-edit"></i> </button>' +
                    '<button type="button" class="btn btn-danger btn-sm eliminar"><i class="fas fa-trash"></i></button>';

                t.row.add([
                    empleado.id,
                    empleado.nombre,
                    empleado.documento,
                    empleado.telefono,
                    empleado.direccion,
                    empleado.email,
                    areaNombre, // Utiliza areaNombre en lugar de empleado.area.nombre directamente
                    botonera
                ]);
            });

            t.draw(false);
			console.log(data);

        },
        error: function () {
            // Manejo de errores
        }
    });
}



function getFila(id) {
    $.ajax({
        type: "GET",
        url: url + "/" + id,
        data: {
            id: id,
        },
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#modal-title").text("Editar Empleado");
            $("#nombre").val(data.nombre);
            $("#documento").val(data.documento);
            $("#telefono").val(data.telefono);
            $("#direccion").val(data.direccion);
            $("#email").val(data.email);
            
            cargarAreas().then(function () {
                // Establecer el área seleccionada
                $("#area").val(data.area.id);
            }).catch(function () {
                console.error("Error al cargar áreas");
            });

            $("#guardar").data("id", data.id);
            $("#guardar").data("bandera", 0);
            $("#modal-update").modal("show");
        },
        error: function () {
            console.error("Error al obtener empleado");
        }
    });
}



function cargarAreas() {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: urlAreas,
            dataType: "json",
            success: function (data) {
                let options = $("#area");
                options.empty();
                options.append($("<option>").val("").text("Seleccione un área"));
                
                $.each(data, function (key, area) {
                    options.append($("<option>").val(area.id).text(area.nombre));
                });

                resolve(); // Resuelve la promesa cuando las áreas se han cargado
            },
            error: function () {
                console.error("Error al cargar las áreas.");
                reject(); // Rechaza la promesa en caso de error
            }
        });
    });
}




function clear() {
    $("#modal-title").text("Nueva Empleado");
    $("#nombre").val("");
    $("#documento").val("");
    $("#telefono").val("");
    $("#direccion").val("");
    $("#email").val("");
    $("#area_id").val("");
    $("#guardar").data("id", 0);
    $("#guardar").data("bandera", 1);
}

$(document).ready(function () {

    $("#tablaEmpleados").DataTable({
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
		cargarAreas();
    });

    $("#guardar").click(function () {

        let bandera = $("#guardar").data("bandera");
        save(bandera);
    })

    $(document).on('click', '.eliminar', function () {
        Swal.fire({
            title: 'Eliminar Empleado',
            text: "¿Esta seguro de querer eliminar este empleado?",
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

