function confirmarConsejo(event, form) {
    event.preventDefault();
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger"
        },
        buttonsStyling: false
    });

    swalWithBootstrapButtons.fire({
        title: "Consejo registrado con éxito",
        text: "¡El consejo se ha guardado correctamente!",
        icon: "success",  
        showCancelButton: false,
        confirmButtonText: "Aceptar",
        background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)', 
        customClass: {
            popup: 'swal-custom-popup'
        },
        didRender: () => {
            const logo = document.createElement('img');
            logo.src = '/img_local/logo-pagina-serviapp.png'; 
            logo.style.width = '155px';
            logo.style.display = 'block';
            logo.style.margin = '0 auto 10px';  

          
            Swal.getIcon().before(logo);

    
            const infoIcon = Swal.getIcon();
            infoIcon.style.position = 'relative';
            infoIcon.style.top = '-10px';  
            infoIcon.style.marginBottom = '10px'; 
        }
    }).then((result) => {
        if (result.isConfirmed) {
            form.submit();  
        }
    });

    return false; 
}


document.querySelector('form').addEventListener('submit', function(event) {
    confirmarConsejo(event, this); 
});