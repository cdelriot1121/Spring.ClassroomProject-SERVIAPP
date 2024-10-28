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
    // Ocultar todas las secciones
    document.getElementById("section-datos-personales").style.display = "none";
    document.getElementById("section-cambiar-contrasena").style.display = "none";
    document.getElementById("section-mis-servicios").style.display = "none";

    // Quitar la clase 'active' de todos los enlaces
    document.querySelectorAll('.list-group-item').forEach(function(link) {
        link.classList.remove('active');
    });

    // Mostrar la sección seleccionada y marcar el enlace como activo
    document.getElementById("section-" + section).style.display = "block";
    document.getElementById("link-" + section).classList.add('active');
}

// Validación de la contraseña en la sección de cambio de contraseña
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

// Función para alternar la visibilidad de la contraseña
function togglePasswordVisibility(inputId) {
    const input = document.getElementById(inputId);
    if (input.type === "password") {
        input.type = "text";
    } else {
        input.type = "password";
    }
}

// Mostrar por defecto la sección "Datos Personales"
showSection("datos-personales");
