package common.model;

import com.google.common.base.Optional;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class EventBuilder {
  private static BinaryDecoder decoder = null;
  private static Optional<SpecificDatumReader<AvroEvent>> reader = Optional.absent();

  public static Event buildEvent(byte buf[]){
    Event event =null;
    try{
      ByteArrayInputStream in =new ByteArrayInputStream(buf);
      decoder = DecoderFactory.get().directBinaryDecoder(in, decoder);
      if (!reader.isPresent()) {
        reader = Optional.of(
                new SpecificDatumReader<AvroEvent>(AvroEvent.class));
      }
      AvroEvent avroEvent = reader.get().read(null, decoder);
      event=EventBuilder.withBody(avroEvent.getBody().array(),
              toStringMap(avroEvent.getHeaders()));
    }catch (Exception e){

    }
    return event;
  }

  /**
   * Helper function to convert a map of CharSequence to a map of String.
   */
  private static Map<String, String> toStringMap(
          Map<CharSequence, CharSequence> charSeqMap) {
    Map<String, String> stringMap =
            new HashMap<String, String>();
    for (Map.Entry<CharSequence, CharSequence> entry : charSeqMap.entrySet()) {
      stringMap.put(entry.getKey().toString(), entry.getValue().toString());
    }
    return stringMap;
  }

  /**
   * Instantiate an Event instance based on the provided body and headers.
   * If <code>headers</code> is <code>null</code>, then it is ignored.
   * @param body
   * @param headers
   * @return
   */
  public static Event withBody(byte[] body, Map<String, String> headers) {
    Event event = new Event();

    if(body == null) {
      body = new byte[0];
    }
    event.setBody(body);

    if (headers != null) {
      event.setHeaders(new HashMap<String, String>(headers));
    }

    return event;
  }

  public static Event withBody(byte[] body) {
    return withBody(body, null);
  }

  public static Event withBody(String body, Charset charset,
      Map<String, String> headers) {

    return withBody(body.getBytes(charset), headers);
  }

  public static Event withBody(String body, Charset charset) {
    return withBody(body, charset, null);
  }

}
