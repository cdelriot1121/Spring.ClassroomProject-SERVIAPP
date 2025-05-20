
$(document).ready(function() {
    $('#usuarios-table').DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            url: '/api/usuarios',
            type: 'GET',
            data: function (d) {
                d.draw = d.draw;
                d.start = d.start;
                d.length = d.length;
            }
        },
        columns: [
            { data: 'nombre' },
            { data: 'email' },
            { data: 'estrato' },
            { data: 'estado' },
            {
                data: null,
                render: function (data, type, row) {
                    return `
                        <form action="${row.estado === 'HABILITADO' ? '/inhabilitar-usuario/' + row.id : '/habilitar-usuario/' + row.id}" method="post">
                            <button type="submit" class="btn ${row.estado === 'HABILITADO' ? 'btn-outline-warning' : 'btn-outline-success'}">
                                ${row.estado === 'HABILITADO' ? 'Inhabilitar' : 'Habilitar'}
                            </button>
                        </form>
                    `;
                }
            }
        ],
        language: {
            search: "Buscar usuario:",
            paginate: {
                previous: "Anterior",
                next: "Siguiente"
            }
        }
    });
});

