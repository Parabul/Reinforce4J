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

    value_loss = tf.keras.losses.MeanSquaredError()(value_true, value_pred)
    policy_pred = tf.nn.softmax(policy_pred, axis=1)
    policy_loss = tf.keras.losses.CategoricalCrossentropy()(policy_true, policy_pred)

    return value_loss + policy_loss

# Function to decode the TFRecord data
def decode_fn(record_bytes):
    feature_description = {
        "input": tf.io.FixedLenFeature([42], dtype=tf.float32),
        "output": tf.io.FixedLenFeature([8], dtype=tf.float32)
    }
    example = tf.io.parse_single_example(record_bytes, feature_description)
    return example["input"], {"value_output": example["output"][:1], "policy_output": example["output"][1:]}

# Load the dataset
files = tf.data.Dataset.list_files(args.Input)
files = list(files.as_numpy_iterator())
decoded_training_dataset = tf.data.TFRecordDataset(files).map(decode_fn).batch(512)

from tensorflow.keras import layers, models, regularizers

# Input layer
input_layer = layers.Input(shape=(42,), name="input_1")
reshaped = layers.Reshape((6, 7, 1))(input_layer)  # Reshape to 6x7x1

# CNN Feature Extraction (Reduced Filters & Layers)
conv1 = layers.Conv2D(16, (3,3), activation='relu', padding='same',
                      kernel_regularizer=regularizers.l2(0.0005))(reshaped)
conv1 = layers.BatchNormalization()(conv1)  # Normalize activations
conv2 = layers.Conv2D(32, (3,3), activation='relu', padding='same',
                      kernel_regularizer=regularizers.l2(0.0005))(conv1)
flat = layers.Flatten()(conv2)

# Fully Connected Layers (Smaller & Regularized)
dense1 = layers.Dense(64, activation='relu', kernel_regularizer=regularizers.l2(0.0005))(flat)
dropout1 = layers.Dropout(0.4)(dense1)  # Increased dropout
dense2 = layers.Dense(32, activation='relu', kernel_regularizer=regularizers.l2(0.0005))(dropout1)
dropout2 = layers.Dropout(0.4)(dense2)

# Value Head
value_dense = layers.Dense(16, activation='relu')(dropout2)
value_output = layers.Dense(1, activation='tanh', name='value_output')(value_dense)

# Policy Head
policy_dense = layers.Dense(16, activation='relu')(dropout2)
policy_output = layers.Dense(7, activation='softmax', name='policy_output')(policy_dense)

# Define the model
model = models.Model(inputs=input_layer, outputs={'value_output': value_output, 'policy_output': policy_output})


model.compile(optimizer=optimizers.Adam(learning_rate=0.001),
              loss=alpha_zero_loss,
              metrics={'value_output': ['mse'], 'policy_output': ['accuracy', 'mse']})

model.summary()

callback = callbacks.EarlyStopping(monitor='loss', min_delta=0.00001, patience=15)

model.fit(decoded_training_dataset, callbacks=[callback], epochs=150)

np.set_printoptions(precision=3, suppress=True)
print(model.predict(np.zeros((1, 42))))
print(model.predict(np.array([[0.0] * 20 + [1.0] + [0.0] * 21])))
print(model.predict(np.array([[1.0, -1.0] * 21])))

model.export(args.Output)
