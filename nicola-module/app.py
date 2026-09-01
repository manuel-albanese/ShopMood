from flask import Flask, request, render_template
import analisi
from flask import Flask, render_template, request, url_for, redirect
from flask_sqlalchemy import SQLAlchemy
from flask_login import (
    LoginManager,
    UserMixin,
    login_user,
    logout_user,
    login_required,
    current_user,
)
from werkzeug.security import generate_password_hash, check_password_hash
import tempfile
import os

# Initialize Flask app
app = Flask(__name__)
app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///db.sqlite"
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
app.config["SECRET_KEY"] = "generationKey"

# Initialize database and login manager
db = SQLAlchemy(app)
login_manager = LoginManager()
login_manager.init_app(app)
login_manager.login_view = "login"


# User model
class Users(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(250), unique=True, nullable=False)
    password = db.Column(db.String(250), nullable=False)


# Create database
with app.app_context():
    db.create_all()


# Load user for Flask-Login
@login_manager.user_loader
def load_user(user_id):
    return Users.query.get(int(user_id))


@app.route("/")
def start():
    return render_template("login.html")


@app.route("/interface")
def interface():
    return render_template("interface.html")


@app.route("/analyze_img", methods=["POST"])
def analyze_img():
    file = request.files["img_input"]

    result = analisi.esegui_analisi_img(file)
    return f"Result: {result}"


@app.route("/analyze_video", methods=["POST"])
def analyze_video():
    file = request.files["video_input"]

    with tempfile.NamedTemporaryFile(delete=False, suffix=".mp4") as tmp:
        tmp_path = tmp.name
        file.save(tmp_path)

    try:
        result = analisi.esegui_analisi_video(tmp_path)
    finally:
        os.remove(tmp_path)

    return f"Result: {result}"


# Register route
@app.route("/register", methods=["GET", "POST"])
def register():
    if request.method == "POST":
        username = request.form.get("username")
        password = request.form.get("password")

        if Users.query.filter_by(username=username).first():
            return render_template("registration.html", error="Username already taken!")

        hashed_password = generate_password_hash(password, method="pbkdf2:sha256")

        new_user = Users(username=username, password=hashed_password)
        db.session.add(new_user)
        db.session.commit()

        return redirect(url_for("login"))
    # if the request is a get
    return render_template("registration.html")


# login route
@app.route("/login", methods=["GET", "POST"])
def login():
    if request.method == "POST":
        username = request.form.get("username")
        password = request.form.get("password")

        user = Users.query.filter_by(username=username).first()

        if user and check_password_hash(user.password, password):
            login_user(user)
            return redirect(url_for("interface"))
        else:
            return render_template("login.html", error="username/password error")
    # if the request is a get
    return render_template("login.html")


if __name__ == "__main__":
    app.run(debug=True)
