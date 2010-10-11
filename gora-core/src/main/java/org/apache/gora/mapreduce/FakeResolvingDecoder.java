/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.gora.mapreduce;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.ResolvingDecoder;
import org.apache.avro.io.parsing.Symbol;
import org.apache.avro.util.Utf8;

/**
 * Avro uses a ResolvingDecoder which resolves two schemas and converts records 
 * written by one to the other, and validates the input. However, gora needs to 
 * write extra information along with the data, so the validation is not consistent 
 * with the grammer generated by Avro. So we need to fake the ResolvingDecoder (which
 * is sadly hard codec into GenericDatumReader) until we can write our own GrammerGenerator
 * extending ResolvingGrammerGenerator of avro.
 */
public class FakeResolvingDecoder extends ResolvingDecoder {

  public FakeResolvingDecoder(Schema schema, Decoder in) throws IOException {
    super(schema, schema, in);
  }
  
  @Override
  public long arrayNext() throws IOException {
    return in.arrayNext();
  }
  
  @Override
  public Symbol doAction(Symbol input, Symbol top) throws IOException {
    return null;
  }
  
  @Override
  public void init(InputStream in) throws IOException {
    this.in.init(in);
  }
  
  @Override
  public long mapNext() throws IOException {
    return in.mapNext();
  }

  @Override
  public double readDouble() throws IOException {
    return in.readDouble();
  }

  @Override
  public int readEnum() throws IOException {
    return in.readEnum();
  }

  @Override
  public int readIndex() throws IOException {
    return in.readIndex();
  }

  @Override
  public long readLong() throws IOException {
    return in.readLong();
  }

  @Override
  public void skipAction() throws IOException {
  }

  @Override
  public long readArrayStart() throws IOException {
    return in.readArrayStart();
  }

  @Override
  public boolean readBoolean() throws IOException {
    return in.readBoolean();
  }

  @Override
  public ByteBuffer readBytes(ByteBuffer old) throws IOException {
    return in.readBytes(old);
  }

  @Override
  public void readFixed(byte[] bytes, int start, int len) throws IOException {
    in.readFixed(bytes, start, len);
  }

  @Override
  public float readFloat() throws IOException {
    return in.readFloat();
  }

  @Override
  public int readInt() throws IOException {
    return in.readInt();
  }

  @Override
  public long readMapStart() throws IOException {
    return in.readMapStart();
  }

  @Override
  public void readNull() throws IOException {
    in.readNull();
  }

  @Override
  public Utf8 readString(Utf8 old) throws IOException {
    return in.readString(old);
  }

  @Override
  public long skipArray() throws IOException {
    return in.skipArray();
  }

  @Override
  public void skipBytes() throws IOException {
    in.skipBytes();
  }

  @Override
  protected void skipFixed() throws IOException {
  }

  @Override
  public void skipFixed(int length) throws IOException {
    in.skipFixed(length);
  }

  @Override
  public long skipMap() throws IOException {
    return in.skipMap();
  }

  @Override
  public void skipString() throws IOException {
  }

  @Override
  public void skipTopSymbol() throws IOException {
  }

  @Override
  public void readFixed(byte[] bytes) throws IOException {
    in.readFixed(bytes);
  }
}
