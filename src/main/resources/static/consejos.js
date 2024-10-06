document.addEventListener('DOMContentLoaded', function () {
    var modal = document.getElementById("modal");
    var modalText = document.getElementById("modalText");
    var buttons = document.querySelectorAll(".boton-consejo");
    var span = document.getElementsByClassName("close")[0];

    // Textos modales usando template literals para soportar múltiples líneas y etiquetas HTML
    var textosModal = {
        agua: `

             <h2>Consejos para Ahorrar Agua</h2>
            <p>
                1. <strong>Cierra el grifo mientras te cepillas los dientes o te lavas las manos</strong>. No es necesario que el agua fluya constantemente durante todo el proceso, de esta manera puedes ahorrar varios litros de agua.<br><br>
                2. <strong>Repara cualquier fuga en grifos o tuberías</strong>. Las fugas pueden parecer pequeñas, pero con el tiempo pueden desperdiciar una gran cantidad de agua. Un grifo que gotea puede perder hasta 30 litros de agua al día.<br><br>
                3. <strong>Usa electrodomésticos de manera eficiente</strong>. Llena completamente la lavadora y el lavavajillas antes de utilizarlos. Estos aparatos consumen la misma cantidad de agua ya sea que estén a media capacidad o llenos.<br><br>
                4. <strong>Toma duchas más cortas</strong>. Reducir el tiempo en la ducha puede generar un ahorro significativo. Un minuto menos en la ducha puede ahorrar hasta 10 litros de agua.<br><br>
                5. <strong>Instala dispositivos ahorradores de agua</strong>. Existen dispositivos como aireadores para grifos, cabezales de ducha de bajo flujo y sistemas de descarga dual para inodoros que reducen el consumo de agua sin sacrificar la funcionalidad.<br><br>
                6. <strong>Riega las plantas en las horas de menos calor</strong>. Evita regar el jardín durante las horas más calurosas del día para minimizar la evaporación. Es mejor regar temprano en la mañana o al anochecer.<br><br>
                7. <strong>Utiliza una cubeta para lavar el coche</strong>. En lugar de usar una manguera, utiliza una cubeta con agua para lavar tu coche. Así puedes ahorrar decenas de litros en cada lavado.<br><br>
                8. <strong>Recoge el agua de lluvia</strong>. Puedes usar el agua de lluvia para regar las plantas, lavar el coche o incluso para la limpieza en el hogar.<br><br>
                9. <strong>Reutiliza el agua cuando sea posible</strong>. El agua que uses para lavar frutas y verduras puede servir para regar las plantas.<br><br>
                10. <strong>Usa inodoros de doble descarga</strong>. Estos permiten usar menos agua en cada descarga.
            </p>`,
        energia: `

               <h2>Consejos para Ahorrar Energia</h2>
            <p>
                1. <strong>Apaga las luces cuando no las necesites</strong>. Aprovecha la luz natural siempre que sea posible para iluminar tu hogar.<br><br>
                2. <strong>Desconecta los electrodomésticos que no estés usando</strong>. Muchos aparatos electrónicos continúan consumiendo energía incluso cuando están apagados.<br><br>
                3. <strong>Utiliza bombillas de bajo consumo o LED</strong>. Estas bombillas duran más y utilizan menos energía que las bombillas incandescentes tradicionales.<br><br>
                4. <strong>Optimiza el uso del aire acondicionado y la calefacción</strong>. Mantén las ventanas y puertas cerradas cuando el aire acondicionado o la calefacción estén en uso. Ajusta los termostatos a temperaturas razonables.<br><br>
                5. <strong>Usa electrodomésticos eficientes</strong>. Los electrodomésticos con calificación energética alta (A+ o superior) consumen menos energía y te ayudarán a ahorrar en la factura.<br><br>
                6. <strong>Lava la ropa con agua fría</strong>. A menos que sea absolutamente necesario, usar agua fría en lugar de caliente reduce el consumo de energía de la lavadora.<br><br>
                7. <strong>Seca la ropa al aire</strong>. Siempre que sea posible, evita usar la secadora y opta por secar la ropa al aire libre o en un tendedero dentro de casa.<br><br>
                8. <strong>Instala temporizadores o sensores de movimiento</strong>. Esto ayuda a encender y apagar las luces automáticamente, ahorrando energía cuando no hay nadie en la habitación.<br><br>
                9. <strong>Programa el calentador de agua</strong>. Si tu calentador tiene la opción, prográmalo para que funcione solo en las horas que lo necesitas y baja la temperatura unos grados para reducir el consumo.
            </p>`,
        gas: `
           <h2>Consejos para Ahorrar Gas</h2>
            <p>
                1. <strong>Mantén limpios los quemadores de la cocina</strong>. Los quemadores sucios pueden causar una combustión ineficiente, lo que aumenta el consumo de gas.<br><br>
                2. <strong>Usa ollas y sartenes del tamaño adecuado</strong>. Usar recipientes pequeños en quemadores grandes desperdicia calor y energía. Ajusta el tamaño de la olla al del quemador.<br><br>
                3. <strong>Tapa las ollas mientras cocinas</strong>. Esto reduce el tiempo de cocción y retiene más calor, lo que ahorra gas.<br><br>
                4. <strong>Revisa periódicamente las instalaciones de gas</strong>. Asegúrate de que no haya fugas ni problemas en las tuberías, conexiones o equipos para evitar un consumo innecesario.<br><br>
                5. <strong>Usa un termostato en el calentador de agua</strong>. Ajusta el termostato del calentador a una temperatura adecuada, generalmente entre 50 y 60 grados Celsius, para no gastar gas en exceso.<br><br>
                6. <strong>Calienta solo el agua que necesites</strong>. No llenes una olla de agua si solo necesitas un poco. Usa la cantidad justa para lo que estás cocinando o preparando.<br><br>
                7. <strong>Desconecta el calentador de agua cuando no esté en uso</strong>. Si no necesitas agua caliente constantemente, puedes apagar el calentador o ajustar un temporizador para que se encienda solo cuando lo necesites.<br><br>
                8. <strong>Usa estufas de gas eficientes</strong>. Considera cambiar a modelos más eficientes que consumen menos gas o utiliza hornos con convección para ahorrar tiempo y gas.<br><br>
                9. <strong>Controla la ventilación en la cocina</strong>. Asegúrate de que tu cocina esté bien ventilada, ya que una mala ventilación puede hacer que se necesite más energía para cocinar y calentar.
            </p>`
    };

    // Mostrar modal con el texto correspondiente
    buttons.forEach(function(button) {
        button.onclick = function() {
            var id = button.id;  // Obtener el id del botón presionado
            modalText.innerHTML = textosModal[id];  // Actualizar el texto del modal
            modal.style.display = "block";  // Mostrar el modal
        };
    });

    // Cerrar modal al presionar la equis
    span.onclick = function() {
        modal.style.display = "none";
    };

    // Cerrar modal si se hace clic fuera de la ventana modal
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };
});
