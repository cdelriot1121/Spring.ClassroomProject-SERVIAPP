function mostrarMensaje(event) {
    event.preventDefault(); 

    document.getElementById("mensaje-exito").style.display = "block";

    setTimeout(() => {
        document.getElementById("formRegistro").submit(); 
    }, 2000); 
}


if (window.location.search.includes('exito=true')) {
    const params = new URLSearchParams(window.location.search);
    const nombreDelUsuario = params.get('nombre') || 'Usuario';

    Swal.fire({
        icon: 'success',
        title: `¡Registro exitoso, ${nombreDelUsuario}!`,
        text: 'Bienvenido a ServiApp. Tu cuenta se ha creado correctamente.',
        confirmButtonColor: '#3085d6',
        confirmButtonText: 'Aceptar',
        background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)',
        customClass: {
            popup: 'swal-custom-popup'
        },
        didRender: () => {
            const logo = document.createElement('img');
            logo.src = '/img_local/logo-pagina-serviapp.png';
            logo.alt = 'Logo de ServiApp';
            logo.style.width = '155px';
            logo.style.display = 'block';
            logo.style.margin = '0 auto 5px'; 
            Swal.getIcon().before(logo);
        }
    });
}
