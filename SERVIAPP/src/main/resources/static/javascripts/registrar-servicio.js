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