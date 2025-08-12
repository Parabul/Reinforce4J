import argparse
import numpy as np
import tensorflow as tf
from tensorflow.keras import layers, models, optimizers, callbacks

# Parse command line arguments
parser = argparse.ArgumentParser()
parser.add_argument("-i", "--Input", help="Training dataset input pattern")
parser.add_argument("-o", "--Output", help="Model output path")
args = parser.parse_args()

print("Training Started")

if args.Input:
    print(f"Input as: {args.Input}")

if args.Output:
    print(f"Output as: {args.Output}")

# Custom loss function similar to AlphaZero's loss
def alpha_zero_loss(y_true, y_pred):
    value_true, policy_true = y_true[:, 0], y_true[:, 1:]
    value_pred, policy_pred = y_pred[:, 0], y_pred[:, 1:]

    # Mean squared error for value prediction
    value_loss = tf.keras.losses.MeanSquaredError()(value_true, value_pred)
    policy_pred = tf.nn.softmax(policy_pred, axis=1)
    # Cross-entropy loss for policy prediction
    policy_loss = tf.keras.losses.CategoricalCrossentropy()(policy_true, policy_pred)

    return value_loss + policy_loss

# Function to decode the TFRecord data
def decode_fn(record_bytes):
    feature_description = {
        "input": tf.io.FixedLenFeature([23], dtype=tf.float32),
        "output": tf.io.FixedLenFeature([10], dtype=tf.float32)
    }
    example = tf.io.parse_single_example(record_bytes, feature_description)
    return example["input"], {"value_output": example["output"][:1], "policy_output": example["output"][1:]}

# Load the dataset
files = tf.data.Dataset.list_files(args.Input)
files = list(files.as_numpy_iterator())
decoded_training_dataset = tf.data.TFRecordDataset(files).map(decode_fn).batch(32)

# Define the model architecture
input_layer = layers.Input(shape=(23,), name = "input_1")

# Hidden layers
hidden_layer1 = layers.Dense(128, activation='relu')(input_layer)
hidden_layer2 = layers.Dense(128, activation='relu')(hidden_layer1)
hidden_layer3 = layers.Dense(128, activation='relu')(hidden_layer2)

# Value output (1 neuron)
value_output = layers.Dense(1, activation='tanh', name='value_output')(hidden_layer3)

# Policy output (9 neurons, one for each possible move)
policy_output = layers.Dense(9, activation='softmax', name='policy_output')(hidden_layer3)

# Create the model
model = models.Model(inputs=input_layer, outputs={'value_output':value_output, 'policy_output':policy_output})

# Compile the model
model.compile(optimizer=optimizers.Adam(learning_rate=0.001),
              loss=alpha_zero_loss,
              metrics={'value_output': ['mse'], 'policy_output': ['accuracy', 'mse']})

model.summary()

# Early stopping callback
callback = callbacks.EarlyStopping(monitor='loss', min_delta=0.0001, patience=3)

# Train the model
model.fit(decoded_training_dataset, callbacks=[callback], epochs=15)

# Make predictions
np.set_printoptions(precision=3, suppress=True)
print(model.predict(np.array([[0.055555556,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0.375,0.5,1,0.8888889,1]])))
print(model.predict(np.array([[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.375, 0.5, 1, 1, 1]])))
print(model.predict(np.array([[0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,0.055555556,-1.0,-1.0,0.0,0.0,0.0]])))

# Save the model
model.export(args.Output)