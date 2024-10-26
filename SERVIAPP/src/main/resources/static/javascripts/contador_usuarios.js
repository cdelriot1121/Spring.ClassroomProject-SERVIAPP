document.addEventListener("DOMContentLoaded", function () {
    const userCountEl = document.getElementById("user-count");
    const targetCount = 150; // Cambia este valor al número real de usuarios
    let count = 0;
    const increment = Math.ceil(targetCount / 100); // Velocidad del conteo

    const counter = setInterval(() => {
        count += increment;
        if (count >= targetCount) {
            userCountEl.textContent = targetCount;
            clearInterval(counter);
        } else {
            userCountEl.textContent = count;
        }
    }, 20); // Velocidad de la animación en milisegundos
});
