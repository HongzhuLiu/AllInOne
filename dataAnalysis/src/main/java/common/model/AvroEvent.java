package common.model;

/**
 * AvroEvent
 *
 * @author chenkui
 * @version 1.0
 * @date 2016/6/13
 */
public class AvroEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AvroEvent\",\"namespace\":\"jstorm.model\",\"fields\":[{\"name\":\"headers\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"body\",\"type\":\"bytes\"}]}");
    public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
    @Deprecated public java.util.Map<CharSequence,CharSequence> headers;
    @Deprecated public java.nio.ByteBuffer body;

    /**
     * Default constructor.
     */
    public AvroEvent() {}

    /**
     * All-args constructor.
     */
    public AvroEvent(java.util.Map<CharSequence,CharSequence> headers, java.nio.ByteBuffer body) {
        this.headers = headers;
        this.body = body;
    }

    public org.apache.avro.Schema getSchema() { return SCHEMA$; }
    // Used by DatumWriter.  Applications should not call.
    public Object get(int field$) {
        switch (field$) {
            case 0: return headers;
            case 1: return body;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }
    // Used by DatumReader.  Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int field$, Object value$) {
        switch (field$) {
            case 0: headers = (java.util.Map<CharSequence,CharSequence>)value$; break;
            case 1: body = (java.nio.ByteBuffer)value$; break;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    /**
     * Gets the value of the 'headers' field.
     */
    public java.util.Map<CharSequence,CharSequence> getHeaders() {
        return headers;
    }

    /**
     * Sets the value of the 'headers' field.
     * @param value the value to set.
     */
    public void setHeaders(java.util.Map<CharSequence,CharSequence> value) {
        this.headers = value;
    }

    /**
     * Gets the value of the 'body' field.
     */
    public java.nio.ByteBuffer getBody() {
        return body;
    }

    /**
     * Sets the value of the 'body' field.
     * @param value the value to set.
     */
    public void setBody(java.nio.ByteBuffer value) {
        this.body = value;
    }

    /** Creates a new AvroEvent RecordBuilder */
    public static Builder newBuilder() {
        return new Builder();
    }

    /** Creates a new AvroEvent RecordBuilder by copying an existing Builder */
    public static Builder newBuilder(Builder other) {
        return new Builder(other);
    }

    /** Creates a new AvroEvent RecordBuilder by copying an existing AvroEvent instance */
    public static Builder newBuilder(AvroEvent other) {
        return new Builder(other);
    }

    /**
     * RecordBuilder for AvroEvent instances.
     */
    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AvroEvent>
            implements org.apache.avro.data.RecordBuilder<AvroEvent> {

        private java.util.Map<CharSequence,CharSequence> headers;
        private java.nio.ByteBuffer body;

        /** Creates a new Builder */
        public Builder() {
            super(AvroEvent.SCHEMA$);
        }

        /** Creates a Builder by copying an existing Builder */
        private Builder(Builder other) {
            super(other);
        }

        /** Creates a Builder by copying an existing AvroEvent instance */
        private Builder(AvroEvent other) {
            super(AvroEvent.SCHEMA$);
            if (isValidValue(fields()[0], other.headers)) {
                this.headers = data().deepCopy(fields()[0].schema(), other.headers);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.body)) {
                this.body = data().deepCopy(fields()[1].schema(), other.body);
                fieldSetFlags()[1] = true;
            }
        }

        /** Gets the value of the 'headers' field */
        public java.util.Map<CharSequence,CharSequence> getHeaders() {
            return headers;
        }

        /** Sets the value of the 'headers' field */
        public Builder setHeaders(java.util.Map<CharSequence,CharSequence> value) {
            validate(fields()[0], value);
            this.headers = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        /** Checks whether the 'headers' field has been set */
        public boolean hasHeaders() {
            return fieldSetFlags()[0];
        }

        /** Clears the value of the 'headers' field */
        public Builder clearHeaders() {
            headers = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        /** Gets the value of the 'body' field */
        public java.nio.ByteBuffer getBody() {
            return body;
        }

        /** Sets the value of the 'body' field */
        public Builder setBody(java.nio.ByteBuffer value) {
            validate(fields()[1], value);
            this.body = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        /** Checks whether the 'body' field has been set */
        public boolean hasBody() {
            return fieldSetFlags()[1];
        }

        /** Clears the value of the 'body' field */
        public Builder clearBody() {
            body = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        @Override
        public AvroEvent build() {
            try {
                AvroEvent record = new AvroEvent();
                record.headers = fieldSetFlags()[0] ? this.headers : (java.util.Map<CharSequence,CharSequence>) defaultValue(fields()[0]);
                record.body = fieldSetFlags()[1] ? this.body : (java.nio.ByteBuffer) defaultValue(fields()[1]);
                return record;
            } catch (Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }
}
