import argparse
import numpy as np
import tensorflow as tf
from tensorflow.keras import layers, models, optimizers, callbacks, regularizers, losses

# Parse command line arguments (optional)
parser = argparse.ArgumentParser()
parser.add_argument("-i", "--input", help="Training dataset input pattern", default="/tmp/connect4_test/training-*.tfrecord")
parser.add_argument("-o", "--output", help="Model output path", default="/tmp/connect4_test/model_v3/")
args = parser.parse_args()

input_path = args.input
output_path = args.output

print(f"Training Started\nInput as: {input_path}\nOutput as: {output_path}")

# Function to decode the TFRecord data
def decode_fn(record_bytes):
    feature_description = {
        "input": tf.io.FixedLenFeature([42], dtype=tf.float32),
        "output": tf.io.FixedLenFeature([8], dtype=tf.float32)
    }
    example = tf.io.parse_single_example(record_bytes, feature_description)
    return example["input"], {"value_output": example["output"][:1], "policy_output": example["output"][1:]}

# Load and preprocess the dataset
files = tf.data.Dataset.list_files(input_path)
dataset = tf.data.TFRecordDataset(files)
dataset = dataset.map(decode_fn)
dataset = dataset.shuffle(buffer_size=128) # Shuffle the dataset
dataset = dataset.batch(32)
dataset = dataset.prefetch(tf.data.AUTOTUNE) # Prefetch for performance

# Model Architecture
input_layer = layers.Input(shape=(42,), name="input_1")
reshaped = layers.Reshape((6, 7, 1))(input_layer)


def alphazero_model(input_shape, reshaped_shape, num_filters, num_residual_blocks, num_policy_moves, num_value_units):
    """
    Creates an AlphaZero-like model using Keras/TensorFlow.

    Args:
        input_shape: Shape of the input tensor (e.g., (board_height, board_width, num_channels)).
        num_filters: Number of filters in the convolutional layers.
        num_residual_blocks: Number of residual blocks.
        num_policy_moves: Number of possible moves for the policy head.
        num_value_units: Number of units in the value head before the final tanh activation.

    Returns:
        A Keras Model.
    """


    input_layer = layers.Input(shape=input_shape, name = "input_1")
    reshaped = layers.Reshape(reshaped_shape)(input_layer)

    # Initial Convolutional Layer
    x = layers.Conv2D(num_filters, (4, 4), padding='valid', activation='relu')(reshaped)

    # Residual Blocks
    for _ in range(num_residual_blocks):
        residual = x
        x = layers.Conv2D(num_filters, (4, 4), padding='same', activation='relu')(x)
        x = layers.Conv2D(num_filters, (4, 4), padding='same')(x)
        x = layers.add([x, residual])
        x = layers.ReLU()(x)

    # Policy Head
    policy_conv = layers.Conv2D(2, (1, 1), padding='same', activation='relu')(x)
    policy_flat = layers.Flatten()(policy_conv)
    # Try sparsemax
    policy_output = layers.Dense(num_policy_moves, activation='softmax', name='policy_output')(policy_flat)

    # Value Head
    value_conv = layers.Conv2D(1, (1, 1), padding='same', activation='relu')(x)
    value_flat = layers.Flatten()(value_conv)
    value_dense = layers.Dense(num_value_units, activation='relu')(value_flat)
    value_output = layers.Dense(1, activation='tanh', name='value_output')(value_dense)

    model = tf.keras.Model(inputs=input_layer, outputs = {'value_output': value_output, 'policy_output': policy_output})
    return model

# Example usage:
input_shape = (42,)  # Raw input
reshaped_shape = (6, 7, 1)
num_filters = 64
num_residual_blocks = 5
num_policy_moves = 7
num_value_units = 128

model = alphazero_model(input_shape, reshaped_shape, num_filters, num_residual_blocks, num_policy_moves, num_value_units)

# Print model summary
model.summary()

# Compile the model
model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.0001),
              loss=[tf.keras.losses.KLDivergence(), 'mse'],
              metrics={'value_output': ['mse'], 'policy_output': ['accuracy', 'categorical_crossentropy']})

# Callbacks
early_stopping = callbacks.EarlyStopping(monitor='loss', min_delta=0.0001, patience=3, restore_best_weights=True)

# Train the model
model.fit(dataset, callbacks=[early_stopping], epochs=30)

# Predictions (for testing)
np.set_printoptions(precision=3, suppress=True)
print(model.predict(np.zeros((1, 42))))
print(model.predict(np.array([[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0]])))
print(model.predict(np.array([[1.0, -1.0] * 21])))

# Save the model
model.export(output_path, format="tf_saved_model")
