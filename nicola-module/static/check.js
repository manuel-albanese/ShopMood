function controlloDimensione() {
    
    const videoElement =  document.getElementById("video_input").files[0]

    console.log(videoElement.size)

    if (videoElement.size >= 10 * 1024 * 1024) {  //if it is not lower than 10 MB, the button is disabled
        console.log("File troppo grande")
        document.getElementById("buttonVideo").disabled = true;
        document.getElementById("videoError").style.display = "block";
    }
    else {
        console.log("File di grandezza consentita")
        document.getElementById("buttonVideo").disabled = false;
        document.getElementById("videoError").style.display = "none";
    }

}

const formImg = document.getElementById('form-img');
const risultatoImg = document.getElementById('risultato-img');
const pulsanteImg = document.getElementById('pulsante-img');

formImg.addEventListener('submit', (event) => {
    event.preventDefault(); 

    pulsanteImg.disabled = true

    const formData = new FormData(formImg); 
    fetch(formImg.action, {
        method: "POST",
        body: formData
    })
    .then(res => res.text())
    .then(data => {
        risultatoImg.textContent = data;
        pulsanteImg.disabled = false
    })
    .catch(err => console.error("Errore invio immagine:", err));

    risultatoImg.textContent = "Elaborazione immagine in corso..."
});


const formVideo = document.getElementById('form-video');
const risultatoVideo = document.getElementById('risultato-video');
const pulsanteVideo =  document.getElementById('buttonVideo');

formVideo.addEventListener('submit', (event) => {
    event.preventDefault(); 

    pulsanteVideo.disabled = true;

    const formData = new FormData(formVideo); 
    fetch(formVideo.action, {
        method: "POST",
        body: formData
    })
    .then(res => res.text())
    .then(data => {
        risultatoVideo.textContent = data;
        pulsanteVideo.disabled = false;
    })
    .catch(err => console.error("Errore invio video:", err));

    risultatoVideo.textContent = "Elaborazione video in corso..."
});