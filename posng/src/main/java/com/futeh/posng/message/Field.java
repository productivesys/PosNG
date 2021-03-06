/*
 * Copyright 2015-2020 Futeh Kao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.futeh.posng.message;

import com.futeh.posng.encoder.Encoder;
import com.futeh.posng.length.FixedLen;
import com.futeh.posng.length.DataLength;

@SuppressWarnings("unchecked")
public abstract class Field<T, V extends Field> extends Component<T, V> {
    protected int maxLength;
    private DataLength dataLength = new FixedLen();
    private Encoder<T> encoder;
    private Padding padding = Padding.NONE;

    public Field() {
    }

    public Encoder<T> getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder<T> encoder) {
        this.encoder = encoder;
    }

    public Encoder<T> encoder() {
        return encoder;
    }

    public V encoder(Encoder<T> encoder) {
        this.encoder = encoder;
        return (V) this;
    }

    public Padding getPadding() {
        return padding;
    }

    public void setPadding(Padding padding) {
        this.padding = padding;
    }

    public Padding padding() {
        return padding;
    }

    public V padding(Padding padding) {
        this.padding = padding;
        return (V) this;
    }

    public V noPadding() {
        this.padding = Padding.NONE;
        return (V) this;
    }

    public V leftPadded() {
        this.padding = Padding.LEFT;
        return (V) this;
    }

    public V rightPadded() {
        padding(Padding.RIGHT);
        return (V) this;
    }

    @Override
    public void validate() {
        if (maxLength <= 0)
            throw new MessageException("maxLength not set");
        getDataLength().validate(this);
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public V maxLength(int maxLength) {
        this.maxLength = maxLength;
        return (V) this;
    }

    public int maxLength() {
        return maxLength;
    }

    public DataLength getDataLength() {
        return dataLength;
    }

    public void setDataLength(DataLength dataLength) {
        this.dataLength = dataLength;
    }

    public DataLength dataLength() {
        return dataLength;
    }

    public V dataLength(DataLength length) {
        this.dataLength = length;
        return (V) this;
    }
}
