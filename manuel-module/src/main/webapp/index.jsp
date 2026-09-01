<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="http://localhost:8080/ShopMood/styles/default.css" type="text/css"/>
    <script type="text/javascript" src="http://localhost:8080/ShopMood/scripts/index.js"></script>
    <title>Raccomandazioni Dinamiche - Progetto Tesi</title>
</head>
<body>

    <div class="card-container">

        <!-- Header del Brand -->
        <div class="brand-header">
            <span class="brand-mark">
                <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M6 8h12l-1.2 10.2a2 2 0 0 1-2 1.8H9.2a2 2 0 0 1-2-1.8L6 8Z" stroke="#ffffff" stroke-width="1.6" stroke-linejoin="round"/>
                    <path d="M9 8V6a3 3 0 0 1 6 0v2" stroke="#ffffff" stroke-width="1.6" stroke-linecap="round"/>
                    <circle cx="12" cy="12.8" r="1.4" fill="#ff6f59"/>
                </svg>
            </span>
            <h1>ShopMood</h1>
            <p class="subtitle">Software di Raccomandazioni di Prodotti</p>
        </div>

        <!-- SEZIONE RACCOMANDAZIONI -->
        <section class="card-section">
            <h2>Sezione Raccomandazioni</h2>
            <p class="section-desc">Inserisci i dati per ricevere le raccomandazioni</p>

            <form action="<%= request.getContextPath() %>/RecommendServlet" method="POST" onsubmit="return validaForm()">

                <div class="form-group">
                    <label for="id_user">ID Utente</label>
                    <input type="text"  id="id_user" name="id_user" placeholder="Es. AA100">
                </div>

                <div class="form-group">
                    <label class="radio-group-label">Modalità di Raccomandazione</label>
                    <div class="radio-group">
                        <label for="hist" class="radio-option">
                            <input type="radio" id="hist" name="modality" value="0" checked>
                            <span>Solo Storico</span>
                        </label>

                        <label for="label_no_ts" class="radio-option">
                            <input type="radio" id="label_no_ts" name="modality" value="1">
                            <span>Label senza Marca Temporale</span>
                        </label>

                        <label for="label_ts" class="radio-option">
                            <input type="radio" id="label_ts" name="modality" value="2">
                            <span>Label con Marca Temporale</span>
                        </label>

                        <label for="text" class="radio-option">
                            <input type="radio" id="text" name="modality" value="3">
                            <span>Solo Note</span>
                        </label>
                        
                       <label for="popularity" class="radio-option">
                            <input type="radio" id="text" name="modality" value="4">
                            <span>Popularity Baseline</span>
                        </label>
                    </div>
                </div>

                <input type="submit" value="Conferma" class="btn btn-primary">
            </form>
        </section>

        <!-- SEZIONE DATE -->
        <section class="card-section">
            <h2>Sezione Date</h2>
            <p class="section-desc">Inserisci una data importante da salvare dopo aver immesso un ID</p>
            <div class="form-group">
                <label for="date">Seleziona Data</label>
                <input type="date" id="date" required>
            </div>
            <input type="button" value="Inserisci Data" onClick="insertDate()" class="btn btn-primary">
        </section>

        <!-- SEZIONE NOTE -->
        <section class="card-section">
            <h2>Sezione Note</h2>
            <p class="section-desc">Aggiungi un commento testuale o vocale al profilo dopo aver immesso un ID</p>

            <div class="form-group">
                <label for="note">Nota Testuale</label>
                <input type="text" id="note" placeholder="Scrivi una nota...">
                <input type="button" value="Conferma la Nota" onClick="insertNote()" class="btn btn-primary" style="margin-top: 12px;">
            </div>

            <div class="form-group">
                <label>Nota Vocale</label>
                <input type="button" id="vocalBtn" value="Inizia il Vocale" onClick="startVocalNote()" class="btn btn-accent">
            </div>

            <p id="status"></p>
        </section>

    </div>

</body>
</html>
