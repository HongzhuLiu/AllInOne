package parse.filter;

import java.util.Map;

/**
 * Created by LHZ on 2016/10/26.
 */
public class CutFields implements Filter{
    private String field;
    private int from;
    private int limit;

    public CutFields(String field,int from,int limit){
        this.field=field;
        this.from=from;
        this.limit=limit;
    }

    @Override
    public void filter(Map<String, Object> resultMap) {
        if(resultMap.containsKey(field)){
            String value= resultMap.get(field)+"";
            resultMap.put(field,value.substring(from,from+limit));
        }
    }
}
