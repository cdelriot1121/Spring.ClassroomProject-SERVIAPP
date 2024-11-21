document.getElementById("link-datos-personales").addEventListener("click", function() {
    showSection("datos-personales");
});

document.getElementById("link-cambiar-contrasena").addEventListener("click", function() {
    showSection("cambiar-contrasena");
});

document.getElementById("link-mis-servicios").addEventListener("click", function() {
    showSection("mis-servicios");
});

function showSection(section) {

    document.getElementById("section-datos-personales").style.display = "none";
    document.getElementById("section-cambiar-contrasena").style.display = "none";
    document.getElementById("section-mis-servicios").style.display = "none";


    document.querySelectorAll('.list-group-item').forEach(function(link) {
        link.classList.remove('active');
    });

  
    document.getElementById("section-" + section).style.display = "block";
    document.getElementById("link-" + section).classList.add('active');
}

function validateForm(event) {
    event.preventDefault(); 

    const currentPassword = document.getElementById('current-password').value;
    const newPassword = document.getElementById('new-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    const errorMessage = document.getElementById('error-message');
    const successMessage = document.getElementById('success-message');

    errorMessage.style.display = 'none';
    errorMessage.textContent = '';
    successMessage.style.display = 'none';

    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/;
    if (!passwordPattern.test(newPassword)) {
        errorMessage.textContent = 'La nueva contraseña no cumple con los requisitos.';
        errorMessage.style.display = 'block';
        return false;
    }

    if (newPassword !== confirmPassword) {
        errorMessage.textContent = 'Las contraseñas no coinciden.';
        errorMessage.style.display = 'block';
        return false;
    }

    successMessage.style.display = 'block';
    
    return true; 
}


function togglePasswordVisibility(inputId) {
    const input = document.getElementById(inputId);
    if (input.type === "password") {
        input.type = "text";
    } else {
        input.type = "password";
    }
}

showSection("datos-personales");



function togglePasswordVisibility(inputId) {
    const input = document.getElementById(inputId);
    if (input.type === "password") {
        input.type = "text";
    } else {
        input.type = "password";
    }
}



function confirmarCambioDatos(event) {
    event.preventDefault();  

    Swal.fire({
        title: '¿Estás seguro de guardar los cambios?',
        text: 'Los cambios no podrán deshacerse.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, guardar cambios',
        cancelButtonText: 'No, cancelar',
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
            document.getElementById('form-datos-personales').submit();  
        } else {
            Swal.fire('No se han realizado cambios', 'Tu información no ha sido modificada', 'info');
        }
    });
}



function confirmarCambioServicio(event) {
    event.preventDefault();  

    Swal.fire({
        title: '¿Estás seguro de guardar los cambios?',
        text: 'Los cambios no podrán deshacerse.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, guardar cambios',
        cancelButtonText: 'No, cancelar',
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
            document.getElementById('form-servicios').submit(); 
        } else {
            Swal.fire('No se han realizado cambios', 'Tu servicio no ha sido modificado', 'info');
        }
    });
}




function confirmarEliminarServicio(event, servicioId) {
    event.preventDefault();  

    Swal.fire({
        title: '¿Estás seguro de eliminar este servicio?',
        text: '¡Esta acción no se puede deshacer!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'No, cancelar',
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
           
            document.getElementById(`form-eliminar-servicio-${servicioId}`).submit(); 
        } else {
            Swal.fire('Eliminación cancelada', 'Tu servicio no ha sido eliminado', 'info');
        }
    });
}




function confirmarCambioContraseña(event) {
    event.preventDefault(); 

    const currentPassword = document.getElementById('current-password').value;
    const newPassword = document.getElementById('new-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;

   
    if (newPassword !== confirmPassword) {
        Swal.fire('Error', 'Las contraseñas no coinciden.', 'error');
        return;
    }

    const passwordPattern = /^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
    if (!passwordPattern.test(newPassword)) {
        Swal.fire('Error', 'La nueva contraseña no cumple con los requisitos.', 'error');
        return;
    }

    Swal.fire({
        title: '¿Estás seguro de cambiar tu contraseña?',
        text: 'Una vez cambiada, no podrás revertirla.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, cambiar contraseña',
        cancelButtonText: 'No, cancelar',
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
         
            document.getElementById('changePasswordForm').submit();
        } else {
            Swal.fire('No se ha realizado ningún cambio', 'Tu contraseña no ha sido modificada', 'info');
        }
    });
}
