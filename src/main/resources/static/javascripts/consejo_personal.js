function toggleConsejos(button) {
    const lista = button.nextElementSibling;
    if (lista.style.display === "none" || !lista.style.display) {
        lista.style.display = "block";
        button.textContent = "Ocultar Consejos";
    } else {
        lista.style.display = "none";
        button.textContent = "Ver Consejos";
    }
}


function toggleConsejos(button) {
    const lista = button.nextElementSibling;
    lista.style.display = lista.style.display === "block" ? "none" : "block";
}

$(document).ready(function () {
    const tabla = $('#tablaConsejos').DataTable({
        paging: true,
        searching: true,
        ordering: true,
        language: {
            url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json'
        },
        initComplete: function () {
            const api = this.api();
            const columnaMes = api.column(1); 
            const columnaAno = api.column(2); 
            const columnaServicio = api.column(0); 

            let meses = new Set();
            let anos = new Set();
            let servicios = new Set();

            // Recolecta valores únicos
            api.rows().every(function () {
                const data = this.data();
                meses.add(data[1]); // Mes
                anos.add(data[2]); // Año
                servicios.add(data[0]); // Servicio
            });

            // Llenar select de Mes
            meses = Array.from(meses).sort();
            meses.forEach(m => {
                $('#filtroMes').append(`<option value="${m}">${m}</option>`);
            });

            // Llenar select de Año
            anos = Array.from(anos).sort();
            anos.forEach(a => {
                $('#filtroAnio').append(`<option value="${a}">${a}</option>`);
            });

            // Llenar select de Servicios
            servicios = Array.from(servicios).sort();
            servicios.forEach(s => {
                $('#filtroServicio').append(`<option value="${s}">${s}</option>`);
            });

            // Función para aplicar filtros
            function filtrarPorMesAnoYServicio() {
                const mes = $('#filtroMes').val();
                const ano = $('#filtroAnio').val();
                const servicio = $('#filtroServicio').val();

                let regex = "";
                if (mes) {
                    regex += `^${mes} `;
                }
                if (ano) {
                    regex += `${ano} `;
                }
                if (servicio) {
                    regex = regex ? regex + servicio : servicio;
                }

                // Aplica el filtro
                api.columns([1, 3, 0]).every(function () {
                    const column = this;
                    column.search(regex, true, false).draw();
                });
            }

            // Aplicar filtro cuando el usuario cambia alguna opción
            $('#filtroMes, #filtroAnio, #filtroServicio').on('change', filtrarPorMesAnoYServicio);
        }
    });
});