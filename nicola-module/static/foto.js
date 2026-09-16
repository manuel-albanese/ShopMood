const video = document.getElementById('video');
const canvas = document.getElementById('canvas');
const foto = document.getElementById('foto');
const pulsanteAttiva = document.getElementById('attiva');
const bottoneScatta = document.getElementById('scatta');
const risultato = document.getElementById('risultato');
const pulsanteImgFoto = document.getElementById('pulsante-img');
const pulsanteVideoFoto = document.getElementById('buttonVideo');

pulsanteAttiva.addEventListener('click', () => {
    navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => {
            video.srcObject = stream;
            video.style.display = 'block';
            bottoneScatta.style.display = 'inline-block';
            pulsanteAttiva.style.display = 'none';
        })
        .catch(err => {
            console.error("Errore accesso webcam: ", err);
            alert("Impossibile accedere alla fotocamera.");
        });
});

bottoneScatta.addEventListener('click', () => {
    const context = canvas.getContext('2d');
    context.drawImage(video, 0, 0, canvas.width, canvas.height);

    const dataUrl = canvas.toDataURL('image/png');
    foto.src = dataUrl;
    foto.style.display = 'block';

    pulsanteImgFoto.disabled = true
    pulsanteVideoFoto.disabled = true
    pulsanteAttiva.disabled = true

    canvas.toBlob(blob => {
        const formData = new FormData();
        formData.append("img_input", blob, "foto.png");

        fetch("/analyze_img", {
            method: "POST",
            body: formData
        })
        .then(res => res.text())
        .then(data => {
            risultato.textContent = data;
            pulsanteImgFoto.disabled = false
            pulsanteAttiva.disabled = false
        })
        .catch(err => console.error("Errore invio foto:", err));
    }, "image/png");

    risultato.textContent = "Elaborazione foto in corso..."
});

bottoneScatta.addEventListener('click', () => {

    video.srcObject.getTracks().forEach(track => track.stop());
    video.style.display = 'none';
    bottoneScatta.style.display = 'none';
    pulsanteAttiva.style.display = 'inline-block'; 
});