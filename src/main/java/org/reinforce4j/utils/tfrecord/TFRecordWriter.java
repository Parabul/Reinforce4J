package org.reinforce4j.utils.tfrecord;

/* Copyright 2016 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import org.tensorflow.example.Example;

public class TFRecordWriter {
  private final DataOutput output;

  public TFRecordWriter(DataOutput output) {
    this.output = output;
  }

  public void write(Example example) {
    write(example.toByteArray());
  }

  public void writeAll(Collection<? extends Example> records) {
    records.stream().map(Example::toByteArray).forEach(this::write);
  }

  public void write(byte[] record, int offset, int length) {
    /**
     * TFRecord format: uint64 length uint32 masked_crc32_of_length byte data[length] uint32
     * masked_crc32_of_data
     */
    byte[] len = toInt64LE(length);
    try {
      output.write(len);
      output.write(toInt32LE(Crc32C.maskedCrc32c(len)));
      output.write(record, offset, length);
      output.write(toInt32LE(Crc32C.maskedCrc32c(record, offset, length)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void write(byte[] record) {
    write(record, 0, record.length);
  }

  private byte[] toInt64LE(long data) {
    byte[] buff = new byte[8];
    ByteBuffer bb = ByteBuffer.wrap(buff);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    bb.putLong(data);
    return buff;
  }

  private byte[] toInt32LE(int data) {
    byte[] buff = new byte[4];
    ByteBuffer bb = ByteBuffer.wrap(buff);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    bb.putInt(data);
    return buff;
  }
}
