
document.getElementById("link-datos-personales").addEventListener("click", function() {
    showSection("datos-personales");
});

document.getElementById("link-cambiar-contrasena").addEventListener("click", function() {
    showSection("cambiar-contrasena");
});

document.getElementById("link-actualizar-foto").addEventListener("click", function() {
    showSection("actualizar-foto");
});

document.getElementById("link-eliminar-cuenta").addEventListener("click", function() {
    showSection("eliminar-cuenta");
});

function showSection(section) {
    document.getElementById("section-datos-personales").style.display = "none";
    document.getElementById("section-cambiar-contrasena").style.display = "none";
    document.getElementById("section-actualizar-foto").style.display = "none";
    document.getElementById("section-eliminar-cuenta").style.display = "none";

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
