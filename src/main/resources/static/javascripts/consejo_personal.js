function toggleConsejos(button) {
    const lista = button.nextElementSibling;
    if (lista.style.display === "none" || !lista.style.display) {
        lista.style.display = "block";
        button.textContent = "Ocultar Consejos";
    } else {
        lista.style.display = "none";
        button.textContent = "Ver Consejos";
    }
}
