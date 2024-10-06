// Obtener elementos
const modal = document.getElementById("mapModal");
const mapFrame = document.getElementById("mapFrame");
const closeModal = document.querySelector(".close");


const mapUrls = {
    afinia: "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d62789.548735707445!2d-75.53117940966355!3d10.394006314239288!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x8ef6253dbdbb68cf%3A0x5c38542579ec3a85!2sAfinia%20Grupo%20EPM!5e0!3m2!1ses-419!2sco!4v1728168356690!5m2!1ses-419!2sco",
    aguas: "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3924.162830073379!2d-75.52236712548707!3d10.408639165871042!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x8ef625d8a7f5ac01%3A0xc4e90dcb8749d04b!2sAGUAS%20DE%20CARTAGENA-%20PRADO!5e0!3m2!1ses-419!2sco!4v1728169179250!5m2!1ses-419!2sco",
    surtigas: "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3924.180837094297!2d-75.51104022548711!3d10.407207765896535!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x8ef6259719e6f2bb%3A0x9568f0d896c48fee!2sSurtigas%20S.A.%20E.S.P.!5e0!3m2!1ses-419!2sco!4v1728169210805!5m2!1ses-419!2sco"
};


document.getElementById("btnAfinia").onclick = function() {
    mapFrame.src = mapUrls.afinia;
    modal.style.display = "block";
};

document.getElementById("btnAguas").onclick = function() {
    mapFrame.src = mapUrls.aguas;
    modal.style.display = "block";
};

document.getElementById("btnSurtigas").onclick = function() {
    mapFrame.src = mapUrls.surtigas;
    modal.style.display = "block";
};


closeModal.onclick = function() {
    modal.style.display = "none";
};

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};
