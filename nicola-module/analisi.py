import csv
from deepface import DeepFace
from PIL import Image
import numpy as np
import cv2 as cv
import datetime


def classifica_emozione(analysis):

    scores = [0, 0, 0]
    dominant_emotion = analysis[0]["dominant_emotion"]

    for emotion, value in analysis[0]["emotion"].items():
        if emotion in ["angry", "fear", "sad", "disgust"]:
            scores[0] += value
        elif emotion == "happy":
            scores[1] += value
        elif emotion == "surprise":
            if dominant_emotion == "happy":
                scores[1] += value
            elif dominant_emotion in ["angry", "fear", "sad", "disgust"]:
                scores[0] += value
            else:
                scores[2] += value
        elif emotion == "neutral":
            scores[2] += value

    soglia = 40

    print(f"Negative Emotion: {scores[0]}")
    print(f"Positive Emotion: {scores[1]}")
    print(f"Neutral Emotion: {scores[2]}")

    assigned_label = "neutral"
    if scores[0] >= soglia and scores[1] >= soglia:
        assigned_label = "neutral"  # segnali contrastanti = stato ambiguo
    elif scores[0] >= soglia:
        assigned_label = "stressed"
    elif scores[1] >= soglia:
        assigned_label = "relaxed"
    else:
        assigned_label = "neutral"

    ordinato = dict(
        sorted(analysis[0]["emotion"].items(), key=lambda x: x[1], reverse=True)
    )
    # ordino le emozioni in ordine decrescente

    filtrato = {k: round(float(v), 2) for k, v in list(ordinato.items())[:3]}

    return dominant_emotion, assigned_label, filtrato


def salva_csv(dominant_emotion, assigned_label, ordinato):
    ora_attuale = datetime.datetime.now()
    with open("result.csv", "r") as f:
        rows = list(csv.reader(f))
        session_id = len(rows) + 1

    items = list(ordinato.items())
    with open("result.csv", "a") as f:
        writer = csv.writer(f, lineterminator="\n")
        writer.writerow(
            [
                f"{session_id:03d}",
                dominant_emotion,
                {
                    items[0][0]: items[0][1],
                    items[1][0]: items[1][1],
                    items[2][0]: items[2][1],
                },
                assigned_label,
                ora_attuale.strftime("%Y-%m-%dT%H:%M:%S"),
            ]
        )


def esegui_analisi_img(file):
    img = Image.open(file.stream).convert("RGB")
    img = np.array(img)

    try:
        analysis = analysis = DeepFace.analyze(
            img, detector_backend="retinaface", silent=True
        )
        dominant_emotion, assigned_label, ordinato = classifica_emozione(analysis)
        salva_csv(dominant_emotion, assigned_label, ordinato)
    except Exception:
        return "Volto non rilevato, provare con un'altra foto"

    return "Elaborazione avvenuta con successo"


def esegui_analisi_video(file, skip_frame=10):
    i = 0
    cap = cv.VideoCapture(file)
    if not cap.isOpened():
        print("Cannot open video")
        return

    emotion_totals = {}
    valid_frames = 0

    while True:
        ret, frame = cap.read()
        if not ret:
            break
        try:
            if i % skip_frame == 0:
                analysis = DeepFace.analyze(frame, silent=True, enforce_detection=False)
                print(f"eseguita analisi {i}")
                for emotion, value in analysis[0]["emotion"].items():
                    emotion_totals[emotion] = (
                        emotion_totals.get(emotion, 0) + value
                    )  # calcolo il valori per tutte le emozioni
                valid_frames += 1
            i = i + 1
        except Exception:
            continue

    cap.release()

    if valid_frames == 0:
        return "Nessun volto rilevato nel video"

    print(emotion_totals)

    avg_emotion = {e: v / valid_frames for e, v in emotion_totals.items()}
    dominant_emotion = max(avg_emotion, key=avg_emotion.get)

    analysis = [{"dominant_emotion": dominant_emotion, "emotion": avg_emotion}]

    dominant_emotion, assigned_label, ordinato = classifica_emozione(analysis)
    salva_csv(dominant_emotion, assigned_label, ordinato)

    return "Elaborazione avvenuta con successo"
