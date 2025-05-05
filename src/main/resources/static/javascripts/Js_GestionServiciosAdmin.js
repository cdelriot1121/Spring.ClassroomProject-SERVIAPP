document.addEventListener('DOMContentLoaded', function() {
    // Función para confirmar eliminación
    const botonesEliminar = document.querySelectorAll('.btn-eliminar-servicio');
    
    botonesEliminar.forEach(boton => {
        boton.addEventListener('click', function(event) {
            event.preventDefault();
            
            const form = this.closest('.eliminar-servicio-form');
            
            Swal.fire({
                title: '¿Estás seguro?',
                text: "No podrás deshacer esta acción",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#28a745',
                cancelButtonColor: ' #d33',
                confirmButtonText: 'Sí, eliminar',
                cancelButtonText: 'No, Cancelar',
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
                    
                   
                    const warningIcon = Swal.getIcon();
                    warningIcon.style.position = 'relative';
                    warningIcon.style.top = '-10px';  
                }
            }).then((result) => {
                if (result.isConfirmed) {
            
                    Swal.fire({
                        title: '¡Eliminado!',
                        text: 'El servicio ha sido eliminado exitosamente.',
                        icon: 'success',
                        confirmButtonColor: '#3085d6',
                        background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)', 
                        customClass: {
                            popup: 'swal-custom-popup' 
                        }
                    }).then(() => {
                        form.submit();
                    });
                } else {
               
                    Swal.fire({
                        title: 'Cancelado',
                        text: 'El servicio no fue eliminado.',
                        icon: 'info',
                        confirmButtonColor: '#3085d6',
                        background: 'linear-gradient(135deg, #f0f8ff, #d0e7ff)',  
                        customClass: {
                            popup: 'swal-custom-popup'  
                        }
                    });
                }
            });
        });
    });
});


document.addEventListener("DOMContentLoaded", function () {
    const serviceCountEl = document.getElementById("service-count");

    // Función para actualizar el contador de servicios
    function actualizarContadorServicios() {
        fetch("/servicios/contador")  // Asegúrate de que esta ruta sea correcta
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener el contador de servicios");
                }
                return response.json();
            })
            .then(data => {
                const targetCount = Number(data);
                if (isNaN(targetCount)) {
                    console.error("El valor recibido no es un número válido:", data);
                    return;
                }
                let count = 0;
                const increment = Math.ceil(targetCount / 100);

                const counter = setInterval(() => {
                    count += increment;
                    if (count >= targetCount) {
                        serviceCountEl.textContent = targetCount;
                        clearInterval(counter);
                    } else {
                        serviceCountEl.textContent = count;
                    }
                }, 20);  // Puedes ajustar el tiempo para la animación
            })
            .catch(error => console.error("Error al obtener el contador de servicios:", error));
    }

    actualizarContadorServicios();  // Llamamos a la función al cargar la página
});
