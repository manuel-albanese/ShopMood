<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, it.unibo.web.beans.*, java.nio.file.*,java.time.LocalTime,java.io.*"%>
<!DOCTYPE html>
<html>
		<head>
			<meta charset="ISO-8859-1">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<link rel="stylesheet" href="<%= request.getContextPath() %>/styles/default.css" type="text/css"/>
			<title>Raccomandazioni Prodotti</title>
		</head>
		<body>
		<div class="card-container">

			<div class="results-header">
				<h1>Prodotti Consigliati</h1>
				<a class="back-link" href="<%= request.getContextPath() %>/">&larr; Torna al modulo</a>
			</div>

			<%
			HashMap<String, ProductRecordDTO>  res = (HashMap<String, ProductRecordDTO>) request.getAttribute("result");
			%>

			<% if (res == null || res.isEmpty()) { %>

				<div class="empty-state">Nessuna raccomandazione disponibile per i criteri selezionati.</div>

			<% } else { %>

				<div class="product-grid">
				<%
					int rank = 1;
					for(ProductRecordDTO r : res.values()){
				%>
					<div class="product-card">
						<div class="product-rank"><%= rank %></div>
						<div class="product-body">
							<p class="product-name"><%= r.getName() %></p>
							<p class="product-meta"><%= r.getCategory() %><span class="product-id">ID <%= r.getParentID()%></span></p>
							<span class="product-score"><span class="score-label">Score</span> <%= String.format("%.2f", r.getScore()) %></span>
						</div>
					</div>
				<%
						rank++;
					}
				%>
				</div>

			<% } %>

		</div>
		</body>
</html>
