document.addEventListener("DOMContentLoaded", function () {
    const userCountEl = document.getElementById("user-count");

   
    function actualizarContadorUsuarios() {
        fetch("/usuarios/contador")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener el contador de usuarios");
                }
                return response.json();
            })
            .then(data => {
                const targetCount = Number(data);
                if (isNaN(targetCount)) {
                    console.error("El valor recibido no es un número válido:", data);
                    return;
                }
                let count = 0;
                const increment = Math.ceil(targetCount / 100);

                const counter = setInterval(() => {
                    count += increment;
                    if (count >= targetCount) {
                        userCountEl.textContent = targetCount;
                        clearInterval(counter);
                    } else {
                        userCountEl.textContent = count;
                    }
                }, 20);
            })
            .catch(error => console.error("Error al obtener el contador de usuarios:", error));
    }

    actualizarContadorUsuarios();
});
