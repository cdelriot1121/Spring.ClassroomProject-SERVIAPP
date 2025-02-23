//Data table
        $(document).ready(function () {
            $('#tablaCortes').DataTable({
                "paging": true,
                "info": true,
                "searching": true,
                "language": {
                    "lengthMenu": "Mostrar _MENU_ registros por página",
                    "zeroRecords": "No se encontraron resultados",
                    "info": "Mostrando página _PAGE_ de _PAGES_",
                    "infoEmpty": "No hay registros disponibles",
                    "infoFiltered": "(filtrado de _MAX_ registros totales)",
                    "search": "Buscar:",
                    "paginate": {
                        "next": "Siguiente",
                        "previous": "Anterior"
                    }
                }
            });
        });


   //sweet alert 
        function confirmarRegistroServicio(event, form) {
            event.preventDefault();
            
            Swal.fire({
                title: "¡Reporte enviado!",
                text: "El fallo ha sido reportado exitosamente.",
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
 