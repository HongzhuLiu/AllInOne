package parse.filter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by LHZ on 2016/10/26.
 */
public class MergeFields implements Filter{
    private ArrayList<String> fields;
    private String field;
    private String sep;

    public MergeFields(ArrayList<String> fields,String sep,String field){
        this.fields=fields;
        this.sep=sep;
        this.field=field;
    }

    @Override
    public void filter(Map<String, Object> resultMap) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fields.size(); i++) {
            String value = fields.get(i);
            if (resultMap.containsKey(value)) {
                if (i == 0) {
                    sb.append(resultMap.get(value) + "");
                } else {
                    sb.append(sep + resultMap.get(value));
                }
                resultMap.remove(value);
            }
        }
        resultMap.put(field, sb.toString());
    }
}
