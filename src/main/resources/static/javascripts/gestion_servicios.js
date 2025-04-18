function validateInput(input) {
    const validMonths = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
    if (!validMonths.includes(input.value) && input.value !== '') {
      input.setCustomValidity('Por favor, selecciona un mes de la lista.');
      input.value = ''; 
    } else {
      input.setCustomValidity('');
    }
  }





  document.addEventListener('DOMContentLoaded', function () {
           
    const cuadrosConsumo = document.getElementById('cuadros-consumo');
    const tipsBox = document.getElementById('tips-box');

    if (cuadrosConsumo) {
        cuadrosConsumo.style.display = 'block';
    }
    if (tipsBox) {
        tipsBox.style.display = 'block';
    }
});






     // Establecer el mes máximo como el actual
     const hoy = new Date();
     const año = hoy.getFullYear();
     const mes = String(hoy.getMonth() + 1).padStart(2, '0');
     document.getElementById('mes').max = `${año}-${mes}`;
 
     // Cambiar automáticamente unidad según servicio seleccionado
     document.getElementById('servicio').addEventListener('change', function () {
         const tipo = this.options[this.selectedIndex].textContent.toLowerCase();
         const unidadInput = document.getElementById('unidad');
 
         if (tipo.includes('agua') || tipo.includes('gas')) {
             unidadInput.value = 'm3';
         } else if (tipo.includes('energía') || tipo.includes('luz') || tipo.includes('electricidad')) {
             unidadInput.value = 'kwh';
         } else {
             unidadInput.value = '';
         }
     });