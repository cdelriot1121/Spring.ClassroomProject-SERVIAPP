function confirmarEliminacion(form) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger"
        },
        buttonsStyling: false
    });

    swalWithBootstrapButtons.fire({
        title: "¿Estás seguro?",
        text: "¡No podrás revertir esto!",
        html: `<div style="text-align: center;">
            <img src="img_local/gato.gif" alt="GIF de gato" style="width: 180px; height: 250px; border-radius: 15px;">
            <p style="margin-top: 50px;">¡No podrás revertir esto!</p>
        </div>`,
        showCancelButton: true,
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "No, cancelar",
        reverseButtons: true,
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
            logo.style.margin = '0 auto 2px';  
            Swal.getIcon().before(logo);
            
            // Subir el ícono de advertencia
            const warningIcon = Swal.getIcon();
            warningIcon.style.position = 'relative';
            warningIcon.style.top = '-5px';  
        }
    }).then((result) => {
        if (result.isConfirmed) {
            form.submit(); 
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            swalWithBootstrapButtons.fire({
                title: "Cancelado",
                text: "El usuario está a salvo :)",
                icon: "info",
                background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)'
            });
        }
    });

    return false;
}