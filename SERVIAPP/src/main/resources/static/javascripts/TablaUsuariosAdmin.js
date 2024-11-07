$(document).ready(function() {
    // Inicializar la tabla con DataTables
    $('#usuarios-table').DataTable({
        responsive: true,    
        searching: true,    
        paging: true,        
        ordering: true,      
        lengthChange: false, 
        info: false,        
        language: {
            search: "Buscar usuario:", 
            paginate: {
                previous: "Anterior", 
                next: "Siguiente"    
            }
        }
    });
});
