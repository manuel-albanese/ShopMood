import os
import csv

os.environ["TF_CPP_MIN_LOG_LEVEL"] = "3"
os.environ["TF_ENABLE_ONEDNN_OPTS"] = "0"

import logging

logging.getLogger("tensorflow").setLevel(logging.ERROR)
from deepface import DeepFace

imgFiles = [
    r"C:\Users\Nicol\OneDrive\Desktop\img1.jpeg",
    r"C:\Users\Nicol\OneDrive\Desktop\angryface.jpeg",
    r"C:\Users\Nicol\OneDrive\Desktop\happyface.jpeg",
    r"C:\Users\Nicol\OneDrive\Desktop\neutral.jpeg",
]

for img in imgFiles:
    analysis = DeepFace.analyze(img, silent=True)
    scores = [0, 0, 0]
    dominant_emotion = analysis[0]["dominant_emotion"]
    assigned_label = "neutral"  # defualt value

    for emotion, value in analysis[0]["emotion"].items():
        if emotion in ["angry", "fear", "sad", "disgust"]:
            scores[0] += value  # stressed
        elif emotion == "happy":
            scores[1] += value  # relaxed
        elif emotion == "surprise":
            # if the context is positive -> relaxed, otherwise -> stressed
            if dominant_emotion in ["happy"]:
                scores[1] += value
            elif dominant_emotion in ["angry", "fear", "sad", "disgust"]:
                scores[0] += value
            else:
                scores[2] += value  # neutral
        elif emotion == "neutral":
            scores[2] += value

    total_points = sum(scores)
    # if the negative emotions are higher than the 50%:
    if scores[0] / total_points > 0.5:
        assigned_label = "stressed"
    # if the positive ones are higher than the 50%
    elif scores[1] / total_points > 0.5:
        assigned_label = "relaxed"
    # otherwise default value

    filtrato = {
        k: round(float(v), 2)
        for k, v in analysis[0]["emotion"].items()
        if k in ["happy", "neutral", "sad"]
    }
    ordinato = dict(sorted(filtrato.items(), key=lambda x: x[1], reverse=True))

    with open("result.csv", "r") as f:
        reader = csv.reader(f)
        rows = list(reader)
        session_id = len(rows) + 1

    with open("result.csv", "a") as f:
        writer = csv.writer(f, lineterminator="\n")

        items = list(ordinato.items())

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
            ]
        )
