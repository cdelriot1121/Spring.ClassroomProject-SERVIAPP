document.addEventListener("DOMContentLoaded", function () {
    const servicioSelect = document.getElementById("servicio");
    const formContent = document.getElementById("form-content");
    const consumoLabel = document.getElementById("consumo-label");
    const consumoInput = document.getElementById("consumo");
    const cuadrosConsumo = document.getElementById("cuadros-consumo");
    const promedioCartagena = document.getElementById("promedio-cartagena");
    const promedioHogar = document.getElementById("promedio-hogar");
    const promedioHabitante = document.getElementById("promedio-habitante");
    const promedioSemanal = document.getElementById("promedio-semanal");
    let unidadMedida = "";

    // Mostrar formulario al seleccionar servicio
    servicioSelect.addEventListener("change", function () {
        formContent.style.display = "block";
        let servicio = servicioSelect.value.toLowerCase();

        // Cambiar unidad de medida acorde al servicio seleccionado
        if (servicio === "agua" || servicio === "gas") {
            consumoLabel.textContent = "Consumo (m³):";
            unidadMedida = "m³";
        } else if (servicio === "energia") {
            consumoLabel.textContent = "Consumo (KWH):";
            unidadMedida = "KWH";
        }
    });

    // Enviar el formulario y mostrar cuadros
    document.getElementById("servicio-form").addEventListener("submit", function (e) {
        e.preventDefault(); 

        const consumo = parseFloat(consumoInput.value);

        promedioCartagena.textContent = `${(consumo * 1.1).toFixed(2)} ${unidadMedida}`;
        promedioHogar.textContent = `${consumo.toFixed(2)} ${unidadMedida}`;
        promedioHabitante.textContent = `${(consumo / 4).toFixed(2)} ${unidadMedida}`;
        promedioSemanal.textContent = `${(consumo / 4.33).toFixed(2)} ${unidadMedida}`;

        // Mostrar cuadros
        cuadrosConsumo.style.display = "flex";
        document.querySelectorAll('.cuadro').forEach(cuadro => {
            cuadro.classList.add('show');
        });
    });

    document.getElementById("cambiar-servicio").addEventListener("click", function () {
        formContent.style.display = "none";
        cuadrosConsumo.style.display = "none";
        servicioSelect.value = "";
        consumoInput.value = "";
    });
});
