from fastapi import FastAPI, UploadFile, File, Form
from pydantic import BaseModel
import joblib
import numpy as np

app = FastAPI()

# Load trained model and label encoder
model = joblib.load('models/crop_model.joblib')
label_encoder = joblib.load('models/label_encoder.joblib')

# -----------------------------
# Pydantic Models
# -----------------------------
class CropInput(BaseModel):
    N: float
    P: float
    K: float
    temperature: float
    humidity: float
    ph: float
    rainfall: float

class FertilizerInput(BaseModel):
    N: float
    P: float
    K: float
    crop: str

# -----------------------------
# Crop Prediction
# -----------------------------
@app.post("/predict")
def predict_crop(data: CropInput):
    X = np.array([[data.N, data.P, data.K, data.temperature, data.humidity, data.ph, data.rainfall]])
    
    # Get probability of each crop
    probs = model.predict_proba(X)[0]  # returns array of probabilities for each class
    top_indices = probs.argsort()[-3:][::-1]  # top 3 crops

    top_crops = label_encoder.inverse_transform(top_indices)
    top_probs = probs[top_indices]

    return {"recommended_crops": list(top_crops), "probabilities": list(top_probs)}


# -----------------------------
# Fertilizer Prediction
# -----------------------------
@app.post("/predictFertilizer")
def predict_fertilizer(data: CropInput):
    # Example rule-based logic
    N, P, K = data.N, data.P, data.K
    recommendations = []

    if N < 50:
        recommendations.append("Ammonium Nitrate")
    if P < 50:
        recommendations.append("Single Super Phosphate")
    if K < 50:
        recommendations.append("Muriate of Potash")
    if not recommendations:
        recommendations.append("Balanced NPK Fertilizer Mix")
    
    return {"recommended_fertilizers": recommendations}

# -----------------------------
# Plant Disease Prediction
# -----------------------------
from fastapi import UploadFile, File, Form

@app.post("/predictDisease")
async def predict_disease(crop: str = Form(...), image: UploadFile = File(...)):
    # Replace with actual ML model logic later
    # For demo, return a mock disease
    return {"detected_disease": f"Leaf Blight on {crop} (Sample Prediction)"}

