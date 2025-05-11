//Consejos.js
document.addEventListener('DOMContentLoaded', function () {
    var modal = document.getElementById("modal");
    var modalTitle = document.getElementById("modalTitle");
    var modalText = document.getElementById("modalText");
    var buttons = document.querySelectorAll(".service-btn");
    var closeModalButton = document.getElementById("closeConsejos");
    
    var textosModal = {
        agua: `
            <h2>Consejos para Ahorrar Agua</h2>
            <p>El agua es un recurso vital y limitado. Adoptar hábitos responsables de consumo te ayudará a reducir tu huella hídrica y ahorrar dinero en tu factura.</p>
            
            <h3>En el Baño</h3>
            <ul>
                <li><strong>Ducha vs. Baño:</strong> Opta por duchas de 5 minutos en lugar de baños. Un baño puede consumir hasta 200 litros, mientras que una ducha eficiente solo 30-40 litros.</li>
                <li><strong>Cierra el grifo:</strong> Mientras te cepillas los dientes, te enjabonas en la ducha o te afeitas, mantén el grifo cerrado. Puede ahorrar hasta 12 litros por minuto.</li>
                <li><strong>Instala aireadores:</strong> Los dispositivos aireadores para grifos reducen el caudal sin disminuir la sensación de presión, ahorrando hasta un 50% de agua.</li>
                <li><strong>Revisa el inodoro:</strong> Un inodoro con fugas puede desperdiciar hasta 200.000 litros anuales. Verifica si hay fugas colocando colorante alimentario en la cisterna; si aparece en la taza sin tirar de la cadena, hay una fuga.</li>
            </ul>
            
            <h3>En la Cocina</h3>
            <ul>
                <li><strong>Llena el lavavajillas:</strong> Utilízalo solo cuando esté completamente lleno y selecciona el programa ecológico cuando sea posible.</li>
                <li><strong>Lavar verduras:</strong> Usa un recipiente en lugar de agua corriente para lavar frutas y verduras, y después puedes reutilizar el agua para tus plantas.</li>
                <li><strong>Descongela en el refrigerador:</strong> Evita descongelar alimentos bajo agua corriente; planifica con antelación y usa el refrigerador.</li>
            </ul>
            
            <h3>En el Hogar</h3>
            <ul>
                <li><strong>Reparación de fugas:</strong> Una gota por segundo equivale a 30 litros diarios desperdiciados. Repara rápidamente cualquier fuga.</li>
                <li><strong>Recoge agua de lluvia:</strong> Utiliza esta agua para regar plantas o limpiar exteriores.</li>
                <li><strong>Riega por la mañana o al atardecer:</strong> Evita regar al mediodía cuando el agua se evapora rápidamente.</li>
            </ul>
        `,
        energia: `
            <h2>Consejos para Ahorrar Energía Eléctrica</h2>
            <p>El consumo responsable de electricidad no solo reduce tu factura, sino que también disminuye la emisión de gases de efecto invernadero. Aquí hay consejos efectivos:</p>
            
            <h3>Iluminación</h3>
            <ul>
                <li><strong>Cambia a LED:</strong> Las bombillas LED consumen hasta un 90% menos de energía y duran hasta 25 veces más que las incandescentes tradicionales.</li>
                <li><strong>Aprovecha la luz natural:</strong> Adapta tus horarios para maximizar el uso de la luz solar y mantén las persianas o cortinas abiertas durante el día.</li>
                <li><strong>Apaga las luces:</strong> Crea el hábito de apagar las luces al salir de una habitación.</li>
                <li><strong>Usa sensores:</strong> Instala sensores de movimiento en pasillos o zonas de paso para que las luces se enciendan solo cuando sea necesario.</li>
            </ul>
            
            <h3>Electrodomésticos</h3>
            <ul>
                <li><strong>Eficiencia energética:</strong> Al comprar nuevos electrodomésticos, busca aquellos con clasificación A+++ que consumen menos energía.</li>
                <li><strong>Refrigerador:</strong> Mantén el refrigerador a 3-5°C y el congelador a -18°C. Cada grado más frío consume 5% más de energía.</li>
                <li><strong>Lavadora y secadora:</strong> Usa programas de baja temperatura (30-40°C) y carga completa. Seca la ropa al aire libre siempre que sea posible.</li>
                <li><strong>Elimina el consumo fantasma:</strong> Los aparatos en modo standby pueden representar hasta el 10% de tu factura eléctrica. Desconéctalos cuando no los uses.</li>
            </ul>
            
            <h3>Climatización</h3>
            <ul>
                <li><strong>Temperatura óptima:</strong> En invierno, mantén la calefacción entre 19-21°C durante el día y 15-17°C por la noche. En verano, ajusta el aire acondicionado a 24-26°C.</li>
                <li><strong>Aislamiento:</strong> Mejora el aislamiento de puertas y ventanas para evitar pérdidas de calor o frío.</li>
                <li><strong>Ventilación eficiente:</strong> Ventila tu hogar durante 10 minutos al día, preferiblemente en las horas más frescas.</li>
                <li><strong>Usa ventiladores:</strong> Un ventilador de techo consume hasta un 90% menos que un equipo de aire acondicionado.</li>
            </ul>
        `,
        gas: `
            <h2>Consejos para Ahorrar Gas</h2>
            <p>El gas es una energía versátil que usamos principalmente para cocinar, calentar agua y en la calefacción. Su uso eficiente no solo reduce costos sino también emisiones contaminantes.</p>
            
            <h3>En la Cocina</h3>
            <ul>
                <li><strong>Ajusta la llama:</strong> La llama no debe sobrepasar el diámetro del utensilio de cocina, ya que desperdiciarías energía.</li>
                <li><strong>Tapa las ollas:</strong> Cocinar con la tapa puesta reduce el tiempo de cocción y ahorra hasta un 25% de gas.</li>
                <li><strong>Aprovecha el calor residual:</strong> Apaga el fuego unos minutos antes de que la comida esté completamente lista y deja que termine de cocinarse con el calor acumulado.</li>
                <li><strong>Hornea por lotes:</strong> Si tienes que hornear varios platos, hazlo de forma consecutiva para aprovechar el calor precalentado.</li>
            </ul>
            
            <h3>Calentador de Agua</h3>
            <ul>
                <li><strong>Temperatura óptima:</strong> Ajusta tu calentador a 50-60°C, es suficiente para uso doméstico y evita derroche de energía.</li>
                <li><strong>Ducharse eficientemente:</strong> Reducir el tiempo de ducha en solo 2 minutos puede ahorrar hasta 10% en tu consumo de gas.</li>
                <li><strong>Mantenimiento regular:</strong> Un calentador limpio y bien mantenido funciona más eficientemente. Programa revisiones anuales.</li>
                <li><strong>Aísla las tuberías:</strong> Coloca aislamiento en las tuberías de agua caliente para reducir pérdidas de calor.</li>
            </ul>
            
            <h3>Calefacción</h3>
            <ul>
                <li><strong>Temperatura confort:</strong> Cada grado por encima de 20°C aumenta el consumo en aproximadamente 7%. Considera usar ropa abrigada dentro de casa.</li>
                <li><strong>Programadores:</strong> Instala termostatos programables para ajustar la temperatura según tus horarios y necesidades.</li>
                <li><strong>Mantenimiento de radiadores:</strong> Purga los radiadores periódicamente para eliminar el aire acumulado que reduce su eficiencia.</li>
                <li><strong>Cierra habitaciones:</strong> Durante el día, cierra las puertas de las habitaciones que no usas para concentrar el calor donde realmente lo necesitas.</li>
                <li><strong>Revisa sellos:</strong> Asegúrate de que puertas y ventanas cierren herméticamente para evitar fugas de calor.</li>
            </ul>
        `
    };
    
    // Agregar evento click a los botones
    buttons.forEach(function(button) {
        button.addEventListener("click", function() {
            // Usar la clase show en vez de modificar display
            modal.classList.add("show");
            
            // Establece el título según el servicio
            if (this.id === "agua") {
                modalTitle.textContent = "Consejos para Ahorrar Agua";
            } else if (this.id === "energia") {
                modalTitle.textContent = "Consejos para Ahorrar Energía";
            } else if (this.id === "gas") {
                modalTitle.textContent = "Consejos para Ahorrar Gas";
            }
            
            modalText.innerHTML = textosModal[this.id];
            
            // Añadir clase para prevenir scroll en body
            document.body.classList.add("modal-open");
        });
    });
    
    // Cerrar modal con el botón X
    closeModalButton.addEventListener("click", function() {
        modal.classList.remove("show");
        document.body.classList.remove("modal-open");
    });
    
    // Cerrar modal haciendo clic fuera del contenido
    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.classList.remove("show");
            document.body.classList.remove("modal-open");
        }
    });
    
    // Prevenir que el scroll se propague fuera del modal
    document.querySelector(".modal-content").addEventListener("click", function(event) {
        event.stopPropagation();
    });
});
