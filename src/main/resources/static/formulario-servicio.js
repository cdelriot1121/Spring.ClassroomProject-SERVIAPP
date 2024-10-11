document.getElementById('servicio').addEventListener('input', function() {
    const servicioSeleccionado = this.value;
    const formContent = document.getElementById('form-content');
    const consumoLabel = document.getElementById('consumo-label');
    const consumoSection = document.getElementById('consumo-section');

    if (servicioSeleccionado === "Agua") {
        consumoLabel.textContent = "Consumo (m³):";
        actualizarCuadrosPromedio("Agua");
    } else if (servicioSeleccionado === "Energia") {
        consumoLabel.textContent = "Consumo (kWh):";
        actualizarCuadrosPromedio("Energia");
    } else if (servicioSeleccionado === "Gas") {
        consumoLabel.textContent = "Consumo (m³):";
        actualizarCuadrosPromedio("Gas");
    } else {
        formContent.style.display = 'none';
        consumoSection.style.display = 'none';
        return;
    }

    formContent.style.display = 'block';
});

function actualizarCuadrosPromedio(servicio) {
    const consumoSection = document.getElementById('consumo-section');
    consumoSection.style.display = 'block';

    if (servicio === "Agua") {
        document.getElementById('promedio-val').innerHTML = "0 m³";
        document.getElementById('habitante-mes').innerHTML = "0 m³";
        document.getElementById('diario-hogar').innerHTML = "0 m³";
        document.getElementById('semanal-hogar').innerHTML = "0 m³";
    } else if (servicio === "Energia") {
        document.getElementById('promedio-val').innerHTML = "0 kWh";
        document.getElementById('habitante-mes').innerHTML = "0 kWh";
        document.getElementById('diario-hogar').innerHTML = "0 kWh";
        document.getElementById('semanal-hogar').innerHTML = "100 kWh";
    } else if (servicio === "Gas") {
        document.getElementById('promedio-val').innerHTML = "30 m³";
        document.getElementById('habitante-mes').innerHTML = "0 m³";
        document.getElementById('diario-hogar').innerHTML = "0 m³";
        document.getElementById('semanal-hogar').innerHTML = "0 m³";
    }
}
