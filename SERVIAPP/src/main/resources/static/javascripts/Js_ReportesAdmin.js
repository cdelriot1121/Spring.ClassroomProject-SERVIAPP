//Sweet Akert
   function confirmarRegistro(event, form) {
        event.preventDefault(); 
        const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                confirmButton: "btn btn-success",
                cancelButton: "btn btn-danger"
            },
            buttonsStyling: false
        });

        swalWithBootstrapButtons.fire({
            title: "Reporte registrado con éxito",
            text: "¡El reporte se ha guardado correctamente!",
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
        confirmarRegistro(event, this); 
    });


//Data Table

    $(document).ready(function() {
        $('#cortesTable').DataTable({
            "language": {
                "url": "https://cdn.datatables.net/plug-ins/1.12.1/i18n/Spanish.json"  
            },
            "paging": true,  
            "searching": true, 
            "ordering": true,  
            "info": true,  
            "lengthChange": true  
        });
    });



//restriccion de fechas
  
    document.addEventListener('DOMContentLoaded', function() {
        const today = new Date();
        const yyyy = today.getFullYear();
        let mm = today.getMonth() + 1; 
        let dd = today.getDate();

     
        if (mm < 10) { mm = '0' + mm; }
        if (dd < 10) { dd = '0' + dd; } 

        const currentDate = yyyy + '-' + mm + '-' + dd;

        document.getElementById('fecha_corte').setAttribute('min', currentDate);
    });

