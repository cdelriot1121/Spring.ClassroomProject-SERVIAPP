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



document.querySelector('.btn_registro').addEventListener('click', function (e) {
    e.preventDefault(); 

    const emailInput = document.querySelector('input[name="email"]').value;

   
    const btnRegistro = this;
    btnRegistro.disabled = true;

   
    fetch(`/usuarios/verificar-email?email=${emailInput}`)
        .then(response => response.json())
        .then(isRegistered => {
            if (isRegistered) {
               
                Swal.fire({
                    icon: 'error',
                    title: `Correo en uso`,
                    text: 'El correo electronico que ingresaste ya esta registrado.',
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
            } else {
           
                document.querySelector('form').submit();
            }
        })
        .catch(error => {
      
            Swal.fire({
                icon: 'error',
                title: 'Error al verificar el correo',
                text: 'Hubo un problema al verificar el correo. Intenta de nuevo más tarde.',
                confirmButtonColor: '#3085d6',
            });
            console.error('Error al verificar el correo:', error);
        })
        .finally(() => {
        
            btnRegistro.disabled = false;
        });
});


