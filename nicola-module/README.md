# About the Application

**Framework:** Flask

## Project Structure

- **`Analisi.py`**: Backend script that analyzes the picture or video and assigns a label.
- **`App.py`**: Contains the routes, defines the authentication system (login/registration), and manages the database with user credentials.
- **`templates/`**
  - **`interface.html`**: Contains the interface where the user can upload a video/picture or take a photo.
  - **`login.html`**: User login page.
  - **`registration.html`**: User registration page.
- **`static/`**
  - **`check.js`**: Checks the video file size to prevent uploading files that are too large, and sends the uploaded picture or video to the backend.
  - **`foto.js`**: Manages camera activation and deactivation, and sends taken photos to the server.