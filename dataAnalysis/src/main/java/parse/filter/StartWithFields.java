package parse.filter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by LHZ on 2016/10/26.
 */
public class StartWithFields implements Filter{
    private String field;
    private ArrayList<Case> cases;

    public StartWithFields(String field,ArrayList<Case> cases){
        this.field=field;
        this.cases=cases;
    }

    @Override
    public void filter(Map<String, Object> resultMap) {
        if (resultMap.containsKey(field)){
            String fieldValue=resultMap.get(field)+"";
            for(Case caseObj:cases){
                String caseStr=caseObj.getCaseStr();
                if(fieldValue.startsWith(caseStr)){
                    caseObj.getFilter().filter(resultMap);
                }
            }
        }
    }
}
