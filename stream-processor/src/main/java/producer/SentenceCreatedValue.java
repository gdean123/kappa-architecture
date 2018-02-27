/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package producer;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class SentenceCreatedValue extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6788185576586724807L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"SentenceCreatedValue\",\"namespace\":\"producer\",\"fields\":[{\"name\":\"sentence\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.String sentence;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public SentenceCreatedValue() {}

  /**
   * All-args constructor.
   * @param sentence The new value for sentence
   */
  public SentenceCreatedValue(java.lang.String sentence) {
    this.sentence = sentence;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return sentence;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: sentence = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'sentence' field.
   * @return The value of the 'sentence' field.
   */
  public java.lang.String getSentence() {
    return sentence;
  }

  /**
   * Sets the value of the 'sentence' field.
   * @param value the value to set.
   */
  public void setSentence(java.lang.String value) {
    this.sentence = value;
  }

  /**
   * Creates a new SentenceCreatedValue RecordBuilder.
   * @return A new SentenceCreatedValue RecordBuilder
   */
  public static producer.SentenceCreatedValue.Builder newBuilder() {
    return new producer.SentenceCreatedValue.Builder();
  }

  /**
   * Creates a new SentenceCreatedValue RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new SentenceCreatedValue RecordBuilder
   */
  public static producer.SentenceCreatedValue.Builder newBuilder(producer.SentenceCreatedValue.Builder other) {
    return new producer.SentenceCreatedValue.Builder(other);
  }

  /**
   * Creates a new SentenceCreatedValue RecordBuilder by copying an existing SentenceCreatedValue instance.
   * @param other The existing instance to copy.
   * @return A new SentenceCreatedValue RecordBuilder
   */
  public static producer.SentenceCreatedValue.Builder newBuilder(producer.SentenceCreatedValue other) {
    return new producer.SentenceCreatedValue.Builder(other);
  }

  /**
   * RecordBuilder for SentenceCreatedValue instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<SentenceCreatedValue>
    implements org.apache.avro.data.RecordBuilder<SentenceCreatedValue> {

    private java.lang.String sentence;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(producer.SentenceCreatedValue.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.sentence)) {
        this.sentence = data().deepCopy(fields()[0].schema(), other.sentence);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing SentenceCreatedValue instance
     * @param other The existing instance to copy.
     */
    private Builder(producer.SentenceCreatedValue other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.sentence)) {
        this.sentence = data().deepCopy(fields()[0].schema(), other.sentence);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'sentence' field.
      * @return The value.
      */
    public java.lang.String getSentence() {
      return sentence;
    }

    /**
      * Sets the value of the 'sentence' field.
      * @param value The value of 'sentence'.
      * @return This builder.
      */
    public producer.SentenceCreatedValue.Builder setSentence(java.lang.String value) {
      validate(fields()[0], value);
      this.sentence = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'sentence' field has been set.
      * @return True if the 'sentence' field has been set, false otherwise.
      */
    public boolean hasSentence() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'sentence' field.
      * @return This builder.
      */
    public producer.SentenceCreatedValue.Builder clearSentence() {
      sentence = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public SentenceCreatedValue build() {
      try {
        SentenceCreatedValue record = new SentenceCreatedValue();
        record.sentence = fieldSetFlags()[0] ? this.sentence : (java.lang.String) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
