package parse.filter;

import java.util.Map;

/**
 * Created by LHZ on 2016/10/26.
 */
public class AddFields implements Filter{
    private Map<String,String> map;

    public AddFields(Map<String,String> map){
        this.map=map;
    }

    @Override
    public void filter(Map<String, Object> resultMap) {
        for(Map.Entry<String,String> entry:map.entrySet()){
            String key=entry.getKey();
            String value=entry.getValue();
            if(resultMap.containsKey(value)){
                resultMap.put(key,resultMap.get(value));
            }
        }
    }
}
