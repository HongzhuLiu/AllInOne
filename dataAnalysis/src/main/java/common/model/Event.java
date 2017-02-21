package common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LHZ on 2016/9/30.
 */
public class Event {
    private Map<String, String> headers;
    private byte[] body;

    public Event() {
        headers = new HashMap<String, String>();
        body = new byte[0];
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        if(body == null){
            body = new byte[0];
        }
        this.body = body;
    }

    @Override
    public String toString() {
        Integer bodyLen = null;
        if (body != null) bodyLen = body.length;
        return "[Event headers = " + headers + ", body.length = " + bodyLen + " ]";
    }
}
