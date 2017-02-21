package parse.filter;

import java.util.Map;
import java.util.Set;

/**
 * Created by LHZ on 2016/10/26.
 */
public class RemoveFields implements Filter{
    private Set<String> fields;

    public RemoveFields(Set<String> fields){
        this.fields=fields;
    }

    @Override
    public void filter(Map<String, Object> resultMap) {
        for(String field:fields){
            resultMap.remove(field);
        }
    }
}
