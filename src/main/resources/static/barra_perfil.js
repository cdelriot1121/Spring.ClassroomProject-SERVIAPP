const profileButton = document.getElementById('profileButton');
    const profileDropdown = document.getElementById('profileDropdown');

    profileButton.addEventListener('click', () => {
        profileDropdown.classList.toggle('active');
    });

    // Cerrar el menú si se hace clic fuera de él
    document.addEventListener('click', (event) => {
        if (!profileButton.contains(event.target) && !profileDropdown.contains(event.target)) {
            profileDropdown.classList.remove('active');
        }
    });