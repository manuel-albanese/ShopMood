const video = document.getElementById('video');
const canvas = document.getElementById('canvas');
const foto = document.getElementById('foto');
const bottoneAttiva = document.getElementById('attiva');
const bottoneScatta = document.getElementById('scatta');
const risultato = document.getElementById('risultato');

bottoneAttiva.addEventListener('click', () => {
    navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => {
            video.srcObject = stream;
            video.style.display = 'block';
            bottoneScatta.style.display = 'inline-block';
            bottoneAttiva.style.display = 'none';
        })
        .catch(err => {
            console.error("Errore accesso webcam: ", err);
            alert("Impossibile accedere alla fotocamera.");
        });
});

// Cattura e invio (invariato)
bottoneScatta.addEventListener('click', () => {
    const context = canvas.getContext('2d');
    context.drawImage(video, 0, 0, canvas.width, canvas.height);

    const dataUrl = canvas.toDataURL('image/png');
    foto.src = dataUrl;
    foto.style.display = 'block';

    bottoneAttiva.disabled = true;

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
            bottoneAttiva.disabled = false;
        })
        .catch(err => console.error("Errore invio foto:", err));
    }, "image/png");

    risultato.textContent = "Elaborazione foto in corso..."
});

bottoneScatta.addEventListener('click', () => {

    video.srcObject.getTracks().forEach(track => track.stop());
    video.style.display = 'none';
    bottoneScatta.style.display = 'none';
    bottoneAttiva.style.display = 'inline-block'; 
});