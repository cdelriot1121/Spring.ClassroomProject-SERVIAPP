//Consejos_admin.js
const servicioSelect = document.getElementById('servicio');
const caracteristicaSelect = document.getElementById('caracteristica');
const consejoField = document.getElementById('consejo-field');
const consejoLabel = document.getElementById('consejo-label');

// función para verificar si ambos campos han sido seleccionados
function verificarSeleccion() {
    const servicio = servicioSelect.value;
    const caracteristica = caracteristicaSelect.value;

    if (servicio && caracteristica) {
    
        consejoLabel.textContent = `Agregar consejo para el servicio "${servicio}" con consumo de tipo "${caracteristica}"`;
        consejoField.style.display = 'block'; // Mostrar el campo de consejos

    } else {
         consejoField.style.display = 'none';
    }
}


servicioSelect.addEventListener('change', verificarSeleccion);
caracteristicaSelect.addEventListener('change', verificarSeleccion);

document.getElementById('dynamic-form').addEventListener('submit', function (event) {
    event.preventDefault(); // evitar recargar la página

    const servicio = servicioSelect.value;
    const caracteristica = caracteristicaSelect.value;
    const consejo = document.getElementById('consejo').value;

    alert(`Consejo agregado:\nServicio: ${servicio}\nConsumo: ${caracteristica}\nConsejo: ${consejo}`);

    this.reset();
    consejoField.style.display = 'none';
});
