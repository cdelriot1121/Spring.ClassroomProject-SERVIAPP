// Crear un nuevo archivo: javascripts/sweet-alerts-handlers.js

// Función para iniciar el proceso de eliminación de servicio
function iniciarEliminacionServicio(servicioId) {
    console.log("Iniciando eliminación del servicio: " + servicioId);
    
    Swal.fire({
        title: '¿Eliminar servicio?',
        text: 'Esta acción eliminará también todos los consumos asociados y no se puede deshacer',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
        background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)', 
        customClass: {
            popup: 'swal-custom-popup',
            confirmButton: 'btn-confirmar',
            cancelButton: 'btn-cancelar'
        },
        didRender: () => {
            const logo = document.createElement('img');
            logo.src = '/img_local/logo-pagina-serviapp.png';
            logo.style.width = '155px';
            logo.style.display = 'block';
            logo.style.margin = '0 auto 10px';
            Swal.getIcon().before(logo);
        }
    }).then((result) => {
        if (result.isConfirmed) {
            // Usar window.location para una redirección simple
            window.location.href = `/servicios/eliminar/${servicioId}`;
        }
    });
}

// Función para iniciar el proceso de eliminación de consumo
function iniciarEliminacionConsumo(consumoId) {
    console.log("Iniciando eliminación del consumo: " + consumoId);
    
    Swal.fire({
        title: '¿Eliminar este consumo?',
        text: 'Esta acción eliminará el registro y los consejos asociados. No se puede deshacer.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
        background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)', 
        customClass: {
            popup: 'swal-custom-popup',
            confirmButton: 'btn-confirmar',
            cancelButton: 'btn-cancelar'
        },
        didRender: () => {
            const logo = document.createElement('img');
            logo.src = '/img_local/logo-pagina-serviapp.png';
            logo.style.width = '155px';
            logo.style.display = 'block';
            logo.style.margin = '0 auto 10px';
            Swal.getIcon().before(logo);
        }
    }).then((result) => {
        if (result.isConfirmed) {
            // Usar window.location para una redirección simple
            window.location.href = `/eliminar-consumo/${consumoId}`;
        }
    });
}