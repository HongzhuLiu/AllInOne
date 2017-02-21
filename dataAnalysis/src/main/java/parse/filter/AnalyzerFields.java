package parse.filter;

import parse.lexer.Lexer;

import java.util.Map;

/**
 * Created by LHZ on 2016/10/27.
 */
public class AnalyzerFields implements Filter{
    private String field;
    private Lexer lexer;

    public AnalyzerFields(String field,Lexer lexer){
        this.field=field;
        this.lexer=lexer;
    }

    @Override
    public void filter(Map<String, Object> resultMap) {
        if(resultMap.containsKey(field)){
            String value=resultMap.get(field)+"";
            Map<String,Object> map=lexer.parse(value);
            resultMap.putAll(map);
        }
    }
}
