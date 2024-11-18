document.addEventListener('DOMContentLoaded', function () {
    // Actualización de empresa según el servicio seleccionado
    document.getElementById('tipo_servicio').addEventListener('input', function () {
        const empresaInput = document.getElementById('empresa');
        const empresaMap = {
            'agua': 'Aguas de Cartagena',
            'energía': 'Afinia',
            'gas': 'Surtigas'
        };

        const empresa = empresaMap[this.value.toLowerCase()] || '';
        empresaInput.value = empresa;
        empresaInput.readOnly = empresa !== '';
    });

    // Función para mostrar alertas con SweetAlert
    const showAlert = (icon, title, text, customClass) => {
        Swal.fire({
            icon,
            title,
            text,
            confirmButtonText: 'Aceptar',
            background: customClass?.background || 'transparent',
            customClass: customClass || {}
        });
    };

    // Error de duplicado 
    const errorDuplicado = document.querySelector('meta[name="errorDuplicado"]');
    if (errorDuplicado && errorDuplicado.content) {
        Swal.fire({
            icon: 'error',
            title: 'Error, Ya tienes este servicio registrado',
         
            confirmButtonText: 'Aceptar',
            background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)',
            customClass: {
                popup: 'swal-custom-popup',
                confirmButton: 'btn-error'
            },
            didRender: () => {
                const logo = document.createElement('img');
                logo.src = '/img_local/logo-pagina-serviapp.png';
                logo.style.width = '155px';
                logo.style.display = 'block';
                logo.style.margin = '0 auto 10px';
                Swal.getIcon().before(logo);

                const errorIcon = Swal.getIcon();
                errorIcon.style.position = 'relative';
                errorIcon.style.top = '-10px';
                errorIcon.style.marginBottom = '10px';
            }
        });
    }

    // Mensaje de éxito
    const mensaje = document.querySelector('meta[name="mensaje"]');
    if (mensaje && mensaje.content) {
        showAlert('success', '¡Éxito!', mensaje.content, {
            popup: 'swal-custom-popup',
            confirmButton: 'btn-confirmar',
            background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)'
        });
    }

    // Confirmar el registro del servicio
    function confirmarRegistroServicio(event, form) {
        event.preventDefault();

        Swal.fire({
            title: "Servicio registrado con éxito",
            text: "¡El servicio se ha guardado correctamente!",
            icon: "success",
            showCancelButton: false,
            confirmButtonText: "Aceptar",
            background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)',
            customClass: {
                popup: 'swal-custom-popup',
                confirmButton: 'btn-confirmar'
            },
            didRender: () => {
                const logo = document.createElement('img');
                logo.src = '/img_local/logo-pagina-serviapp.png';
                logo.style.width = '155px';
                logo.style.display = 'block';
                logo.style.margin = '0 auto 10px';
                Swal.getIcon().before(logo);

                const successIcon = Swal.getIcon();
                successIcon.style.position = 'relative';
                successIcon.style.top = '-10px';
                successIcon.style.marginBottom = '10px';
            }
        }).then((result) => {
            if (result.isConfirmed) {
                form.submit();
            }
        });
    }

    const form = document.getElementById("formRegistroServicio");
    form.addEventListener("submit", function (event) {
        event.preventDefault(); 

        const tipoServicio = document.getElementById("tipo_servicio").value;
        const empresa = document.getElementById("empresa").value;
        const poliza = document.querySelector('[name="poliza"]').value;
        const habitantes = document.querySelector('[name="habitantes"]').value;

        // Validación básica de campos vacíos
        if (!tipoServicio || !empresa || !poliza || !habitantes) {
            showAlert('error', 'Campos incompletos', 'Por favor, completa todos los campos antes de registrar el servicio.');
            return;
        }

        // Validación AJAX
        fetch('/servicios/validar-servicio', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ tipo_servicio: tipoServicio })
        })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
          
                Swal.fire({
                    icon: 'error',
                    title: 'Error, Ya tienes este servicio registrado',
                   
                    confirmButtonText: 'Aceptar',
                    background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)',
                    customClass: {
                        popup: 'swal-custom-popup',
                        confirmButton: 'btn-error'
                    },
                    didRender: () => {
                        const logo = document.createElement('img');
                        logo.src = '/img_local/logo-pagina-serviapp.png';
                        logo.style.width = '155px';
                        logo.style.display = 'block';
                        logo.style.margin = '0 auto 10px';
                        Swal.getIcon().before(logo);

                        const errorIcon = Swal.getIcon();
                        errorIcon.style.position = 'relative';
                        errorIcon.style.top = '-10px';
                        errorIcon.style.marginBottom = '10px';
                    }
                });
            } else {
                // Si todo está bien, confirmar el registro del servicio
                confirmarRegistroServicio(event, form);
            }
        })
        .catch(error => {
            console.error('Error en la solicitud:', error);
            showAlert('error', 'Error', 'Ocurrió un error al procesar tu solicitud.');
        });
    });
});
