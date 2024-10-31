function mostrarMensaje(event) {
    event.preventDefault(); 

    document.getElementById("mensaje-exito").style.display = "block";

    setTimeout(() => {
        document.getElementById("formRegistro").submit(); 
    }, 2000); 
}
