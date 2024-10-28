document.getElementById('servicio').addEventListener('input', function() {
    const servicioSeleccionado = this.value;
    const formContent = document.getElementById('form-content');
    const consumoLabel = document.getElementById('consumo-label');

    // Actualizamos el label del consumo según el servicio seleccionado
    if (servicioSeleccionado === "Agua") {
        consumoLabel.textContent = "Consumo (m³):";
    } else if (servicioSeleccionado === "Energia") {
        consumoLabel.textContent = "Consumo (kWh):";
    } else if (servicioSeleccionado === "Gas") {
        consumoLabel.textContent = "Consumo (m³):";
    } else {
        formContent.style.display = 'none';
        return;
    }

    // Mostramos el formulario
    formContent.style.display = 'block';
});

// Manejar el envío del formulario
document.getElementById('servicio-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita el envío normal del formulario

    // Mostramos el título de resultados
    const consumoSection = document.getElementById('consumo-section');
    consumoSection.style.display = 'block';

    // Eliminamos cuadros anteriores si existen
    while (consumoSection.firstChild) {
        consumoSection.removeChild(consumoSection.firstChild);
    }

    // Agregamos el título de resultados
    const tituloResultados = document.createElement('h2');
    tituloResultados.textContent = 'Estos son tus resultados';
    consumoSection.appendChild(tituloResultados);

    // Agregar nuevos cuadros de resultados
    crearCuadroResultado('Promedio de Consumo', '0', servicioSeleccionado, consumoSection);
    crearCuadroResultado('Consumo por habitante/mes', '0', servicioSeleccionado, consumoSection);
    crearCuadroResultado('Consumo diario del hogar', '0', servicioSeleccionado, consumoSection);
    crearCuadroResultado('Consumo semanal del hogar', '0', servicioSeleccionado, consumoSection);
});

function crearCuadroResultado(titulo, valor, servicio, contenedor) {
    // Crear cuadro
    const cuadro = document.createElement('div');
    cuadro.style.border = '1px solid #ccc';
    cuadro.style.padding = '10px';
    cuadro.style.margin = '10px';
    cuadro.style.textAlign = 'center';
    cuadro.style.backgroundColor = '#f9f9f9';
    cuadro.style.width = '200px';
    cuadro.style.display = 'inline-block';

    // Título
    const tituloElemento = document.createElement('h3');
    tituloElemento.textContent = titulo;
    cuadro.appendChild(tituloElemento);

    // Valor
    const valorElemento = document.createElement('p');
    let unidades = servicio === "Energia" ? "kWh" : "m³";
    valorElemento.textContent = `${valor} ${unidades}`;
    cuadro.appendChild(valorElemento);

    // Agregar cuadro al contenedor
    contenedor.appendChild(cuadro);
}
