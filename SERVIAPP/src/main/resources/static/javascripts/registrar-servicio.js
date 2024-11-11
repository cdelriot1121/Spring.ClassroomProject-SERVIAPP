document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('tipo_servicio').addEventListener('input', function() {
        const empresaInput = document.getElementById('empresa');
        const servicio = this.value.toLowerCase();

        let empresa = '';
        switch (servicio) {
            case 'agua':
                empresa = 'Aguas de Cartagena';
                break;
            case 'energía':
                empresa = 'Afinia';
                break;
            case 'gas':
                empresa = 'Surtigas';
                break;
        }

        empresaInput.value = empresa;
        empresaInput.readOnly = empresa !== '';
    });
});

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

    return false;
}

document.querySelector('form').addEventListener('submit', function(event) {
    confirmarRegistroServicio(event, this);
});


