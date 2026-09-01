
The files represents a Java project for a web application which runs on the Tomcat server. 

To access persistent data (JSON files from the Amazon Review Dataset 2023 and CSV files from Nicola) I used a DAO pattern. This architecture is
formed by 3 packages. it.unibo.web.dao, it.unibo.web.dao.files and it.unibo.web.beans. The first one contains only interfaces and an abstract
factory; the second one the concrete implementations to access those types of files (it includes external libreries like Jackson); the third one
various beans and DTO classes. 

The textual/vocal notes are also saved in a CSV file, with every entry containing a timestamp, the id_user and the relative note. 

The business logic is separated from the rest of the application (package it.unibo.web.dao.strategy) and, like the name suggests, it's implemented
throughout a Strategy Pattern. We have an abstract interface which declares the signature of the method RecommendProducts. Then, for every
type of algorithm (like the Popolarity Baseline) there is a concrete implementation. 

There are 5 versions of the recommender algorithm:

-popularity baseline (which is based on the notorious MostPop): in this case we count the number of interactions/reviews for each product to determine
the popularity. Aonther choice could be to use explicit ratings?

-history-only (Heuristic): for one single user, reviews are analyzed to suggest products rated positively (we use explicit feedback). The score
attributed to a recommended product is equal to the rating given by the user after a purchase. There are 2 static variables: event and constant. They are 
here to allow a possible tuning if we ever change the types of analyzed events (maybe viewed and not purchased products) or the scale of rating

-label without timestamp (Heuristic): with a contextual post-filtering action applied on history-only, the label can give a 50% increase or decrease of the base rating. To simplify the process, I have made a static mapping of all categories present in the Amazon Reviews dataset. The mapping is inspired
by the paper Emotional Responses to Brands and Product Categories written by Percy, Larry; Hansen, Flemming; Randrup, Rolf. It describes emotional
responses of an individual to certain brands and activities. 

-label with timestamp (partly Heuristic): there is another layer of contextual post-filtering. We take scores given by the label without timestamp 
algorithm and we apply several factors. 

First we use a method to calculate the seasonal distance between now and the considered purchase. The maximum
distance is 6 months (like August and January) and it lowers by 50% the base score. With 0 distance we have an increase of 50%.

The user can also insert dates into the site (like a birthday or an anniversary). In this way, if there are products bought in that day of the year,
they will be strongly proposed again at the same day of the interaction (the year is irrelevant). 

Lastly, I applied a time decay exponential function as described in the paper Time-aware recommender systems: a comprehensive
survey and analysis of existing evaluation protocols by Pedro G. Campos · Fernando Díez ·Iván Cantador. (although it's a second hand 
citation, since the original came from Time Weight Collaborative Filtering by Ding, Li). But I'm not sure if it's really necessary?


-text-only(Heuristic): All notes made by a user are analyzed. In this case, we assume that certain key words in the note can be found in the
titles of the products in the catalogue. For this reasons, we extract the reduced titles (articles like 'The' are not to be included) of all elements 
and we see if it is contained by the note. 

Since the application follows Java Model 2, the Controller function is managed by Servlets present in the package it.unibo.web.servlets. There
is a Note Servlet to insert notes in the CSV file, a DateServlet to insert dates into a context attribute and a RecommendServlet for the 
recommendation. Every recommendation needs the knowledge of a user ID since there is no login or registration. 

View is composed of 2 JSP, index.jsp and products.jsp. First one is the welcome page where you can interact with the app. The second simply shows
the recommendation. There is also a javascript file, index.js, which sends AJAX requests for the insertion of notes and dates. Style is realized
through out default.css.