
function insertDate(){
	
	var date = document.getElementById("date").value;
	var id_user = document.getElementById("id_user").value;
	
	console.log(date);

	if(id_user=="") alert("Inserire un ID");
	else{
		fetch("http://localhost:8080/ShopMood/DateServlet",
		{
           method: "POST",
           headers: {
           "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
           },
             body: "date=" + encodeURIComponent(date) + "&id_user=" + encodeURIComponent(id_user)
         })
        .then( (response) => {
		      if(response.ok) console.log("Operazione Avvenuta con Successo");		
              else  throw new Error("Errore HTTP");
              
	        }			
	     )	
	}
	
}

function insertNote(){
	var note = document.getElementById("note").value;
	var id_user = document.getElementById("id_user").value;
	
	
	if(note.length==0 || id_user=="") alert("Inserire una nota completa e un ID");
	else{
		fetch("http://localhost:8080/ShopMood/NoteServlet",
		{
           method: "POST",
           headers: {
           "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
           },
             body: "note=" + encodeURIComponent(note) + "&id_user=" + encodeURIComponent(id_user)
         })
        .then( (response) => {
		      if(response.ok) {
					console.log("Operazione Avvenuta con Successo");
		      		document.getElementById("status").innerHTML="Nota Registrata";	  
			  }	
              else  throw new Error("Errore HTTP");
              
	        }			
	     )	
	}
	
}


function startVocalNote(){
	const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
	document.getElementById("status").innerHTML="Nota In Corso";
	
	if (!SpeechRecognition) {
        alert("Il tuo browser non supporta il riconoscimento vocale.");
        return;
    }
    
  	const recognition =  new SpeechRecognition();
  	recognition.lang = 'it-IT';
  	recognition.interimResults = false;
  	
  	recognition.onresult = (event) => {
  		const transcript = event.results[0][0].transcript;
  		var id_user = document.getElementById("id_user").value;
 	 	console.log(transcript);
 	 	
 	 	if(transcript.length==0 || id_user==""){
			  alert("Inserire una nota completa e un ID"); 
			  document.getElementById("status").innerHTML="Nota Fallita";
		}else{
 	 	fetch("http://localhost:8080/ShopMood/NoteServlet",
		{
           method: "POST",
           headers: {
           "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
           },
             body: "note=" + encodeURIComponent(transcript) + "&id_user=" + encodeURIComponent(id_user)
         })
        .then( (response) => {
		      if(response.ok) {
					console.log("Operazione Avvenuta con Successo");
		      		document.getElementById("status").innerHTML="Nota Registrata";	  
			  }	
              else  throw new Error("Errore HTTP");
              
	        }	
              
	        		
	     )
	   }
	};
	
	recognition.onerror = (event) => {
        console.error("Errore durante il riconoscimento:", event.error);
        document.getElementById("status").innerHTML="Nota Fallita";
    };
  	
	recognition.start();
	
}

function validaForm() {
	var id_user = document.getElementById("id_user").value;
	 	 	if(id_user==""){
			  alert("Inserire un ID"); 
			  return false; 
		}
    var radioSelezionato = document.querySelector('input[name="modality"]:checked');
    if (!radioSelezionato) {
        alert("Seleziona una modalità di raccomandazione.");
        return false; 
    }

    return true; 
}

