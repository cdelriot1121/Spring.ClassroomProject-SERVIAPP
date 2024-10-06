// script.js
document.addEventListener('DOMContentLoaded', function() {
    // Obtener el modal
    var modal = document.getElementById("termsModal");

    // Obtener el enlace que abre el modal
    var openModalButton = document.getElementById("openModal");

    // Obtener el elemento <span> que cierra el modal
    var closeModalButton = document.getElementsByClassName("close")[0];

    // Cuando el usuario hace clic en el enlace, abrir el modal
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
