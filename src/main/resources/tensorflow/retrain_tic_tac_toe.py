import numpy as np
from tensorflow.keras.layers import Input, Dense
from tensorflow.keras.models import Model
from tensorflow.keras.optimizers import Adam

import tensorflow as tf
from tensorflow import keras


def alpha_zero_loss(y_true, y_pred):
    # Split y_true and y_pred into value and policy parts
    value_true, policy_true = y_true[:, 0], y_true[:, 1:]
    value_pred, policy_pred = y_pred[:, 0], y_pred[:, 1:]

    # Value loss (MSE)
    value_loss = keras.losses.mean_squared_error(value_true, value_pred)

    # Policy loss (Cross-Entropy)
    policy_loss = keras.losses.categorical_crossentropy(policy_true, policy_pred)

    # Combine the losses
    total_loss = value_loss + policy_loss

    return total_loss


print("training")


def decode_fn(record_bytes):
    example = tf.io.parse_single_example(
        record_bytes,

        {"input": tf.io.FixedLenFeature([9], dtype=tf.float32),
         "output": tf.io.FixedLenFeature([10], dtype=tf.float32)}

    )
    return example["input"], {"value_output": example["output"][:1], "policy_output": example["output"][1:]}


files_path = "/home/anarbek/tmp/simple_v3.tfrecord"
print(files_path)

files = tf.data.Dataset.list_files(files_path)
files = list(files.as_numpy_iterator())

decoded_training_dataset = tf.data.TFRecordDataset(files).map(decode_fn).batch(512)

for input_tensor, output_dict in decoded_training_dataset.take(1):
    print("Input:", input_tensor)
    print("Value Output:", output_dict["value_output"])
    print("Policy Output:", output_dict["policy_output"])

input_layer = Input(shape=(9,))

# Hidden layers
hidden_layer1 = Dense(128, activation='relu')(input_layer)
hidden_layer2 = Dense(128, activation='relu')(hidden_layer1)
hidden_layer3 = Dense(128, activation='relu')(hidden_layer2)
hidden_layer4 = Dense(128, activation='relu')(hidden_layer3)

# Value output (1 neuron)
value_output = Dense(1, activation='tanh', name='value_output')(hidden_layer4)

# Policy output (9 neurons, one for each possible move)
policy_output = Dense(9, activation='softmax', name='policy_output')(hidden_layer4)

# Combine value and policy outputs
model = Model(inputs=input_layer, outputs=[value_output, policy_output])

# Compile the model with different losses for value and policy
model.compile(optimizer=Adam(learning_rate=0.001),
              loss=alpha_zero_loss,
              metrics={'value_output': ['mse'], 'policy_output': ['accuracy', 'mse']})

model.summary()

callback = keras.callbacks.EarlyStopping(monitor='loss', min_delta=0.00001, patience=3)

model.fit(decoded_training_dataset, callbacks=[callback], epochs=15)
np.set_printoptions(precision=3)
np.set_printoptions(suppress=True)
print(model.predict([[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]]))
print(model.predict([[0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0]]))
print(model.predict([[1.0, 1.0, -1.0, 0.0, 1.0, 0.0, 0.0, 0.0, -1.0]]))
print(model.predict([[-1.0, -1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0]]))

model.save("/home/anarbek/tmp/tic_tac_toe_model/re_tf_model", save_format="tf", overwrite=True)
