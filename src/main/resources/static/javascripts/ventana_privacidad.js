// ventaja_privacidad.js
document.addEventListener('DOMContentLoaded', function() {
    
    var modal = document.getElementById("termsModal-privacidad");

    
    var openModalButton = document.getElementById("openModal-privacidad");

    var closeModalButton = document.getElementsByClassName("cerrar-privacidad")[0];

    openModalButton.onclick = function() {
        modal.style.display = "block";
    }

    // Cerrar la ventana emergente al hacer clic en "X"
    closeModalButton.onclick = function() {
        modal.style.display = "none";
    }

    // Cerrar la ventana emergente al hacer clic fuera de ella
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
});
