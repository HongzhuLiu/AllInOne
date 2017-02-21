package parse.lexer;

import java.util.Map;

/**
 * Created by LHZ on 2016/10/26.
 */
public class DelimiterLexer extends BaseLexer {
    private String[] fields;
    private String delimIt;
    public DelimiterLexer(String[] fields,String delimit){
        this.fields=fields;
        this.delimIt=delimit;
    }

    @Override
    public Map<String, Object> parse(String line) {
        String[] strs=line.split(delimIt);
        int length=strs.length>fields.length? fields.length:strs.length;
        for(int i=0;i<length;i++){
            resultMap.put(fields[i],strs[i]);
        }
        filter(resultMap);
        return resultMap;
    }
}
