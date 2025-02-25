import tensorflow as tf
import numpy as np
from tensorflow.keras.layers import TFSMLayer
import argparse

# Parse command line arguments
parser = argparse.ArgumentParser()
parser.add_argument("-i", "--Input", help="Model file path to load")
args = parser.parse_args()

print("Inference Started")

# Ensure input path is provided
if args.Input:
    model_path = args.Input
    print(f"Model loaded from: {model_path}")
else:
    raise ValueError("Model input path is required!")

# Load the SavedModel using TFSMLayer
model = tf.keras.Sequential([
    TFSMLayer(model_path, call_endpoint='serving_default')
])

# Hardcoded examples for inference
input_data_examples = [
    np.array([[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]]),
    np.array([[0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0]]),
    np.array([[1.0, 1.0, -1.0, 0.0, 1.0, 0.0, 0.0, 0.0, -1.0]]),
    np.array([[-1.0, -1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0]])
]

# Run inference on each hardcoded example
for i, input_data in enumerate(input_data_examples):
    print(f"Prediction for example {i + 1}: {input_data}")
    predictions = model(input_data)  # Inference

    value_output = predictions[0][0]  # The value output (scalar)
    policy_output = predictions[1]  # The policy output (array of 9 probabilities)

    print(f"Value Output: {value_output}")
    print(f"Policy Output: {policy_output}\n")
