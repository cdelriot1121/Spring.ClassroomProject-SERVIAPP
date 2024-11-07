$(document).ready(function() {
    $('#servicios-table').DataTable({
        responsive: true,
        searching: true,
        paging: true,
        ordering: true,
        lengthChange: false,
        info: false,
        language: {
            search: "Buscar servicio:",
            paginate: {
                previous: "Anterior",
                next: "Siguiente"
            }
        }
    });
});