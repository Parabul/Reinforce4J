package org.reinforce4j.utils.tfrecord;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.junit.Test;
import org.tensorflow.example.Example;
import org.tensorflow.example.Feature;
import org.tensorflow.example.Features;
import org.tensorflow.example.Int64List;

public class TfRecordTest {

  @Test
  public void shouldWriteAndRead() throws IOException {
    final Example record =
        Example.newBuilder()
            .setFeatures(
                Features.newBuilder()
                    .putFeature(
                        "key",
                        Feature.newBuilder()
                            .setInt64List(Int64List.newBuilder().addValue(42L))
                            .build()))
            .build();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
    TFRecordWriter writer = new TFRecordWriter(dataOutputStream);
    writer.write(record);
    dataOutputStream.close();

    TFRecordReader tfRecordReader =
        new TFRecordReader(new ByteArrayInputStream(outputStream.toByteArray()), true);

    assertThat(Example.parseFrom(tfRecordReader.read())).isEqualTo(record);
  }

  @Test
  public void shouldWriteAndReadTwo() throws IOException {
    final Example recordOne =
        Example.newBuilder()
            .setFeatures(
                Features.newBuilder()
                    .putFeature(
                        "key",
                        Feature.newBuilder()
                            .setInt64List(Int64List.newBuilder().addValue(42L))
                            .build()))
            .build();

    final Example recordTwo =
        Example.newBuilder()
            .setFeatures(
                Features.newBuilder()
                    .putFeature(
                        "abc",
                        Feature.newBuilder()
                            .setInt64List(Int64List.newBuilder().addValue(17L))
                            .build()))
            .build();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
    TFRecordWriter writer = new TFRecordWriter(dataOutputStream);
    writer.writeAll(ImmutableList.of(recordOne, recordTwo));
    dataOutputStream.close();

    TFRecordReader tfRecordReader =
        new TFRecordReader(new ByteArrayInputStream(outputStream.toByteArray()), true);

    assertThat(Example.parseFrom(tfRecordReader.read())).isEqualTo(recordOne);
    assertThat(Example.parseFrom(tfRecordReader.read())).isEqualTo(recordTwo);
  }
}
