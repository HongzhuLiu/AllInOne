package parse.lexer;

import java.util.Map;

/**
 * Created by LHZ on 2016/10/27.
 */
public class DelimitWithKeyMap extends BaseLexer {
    private String delimIt;
    private String tab;

    public DelimitWithKeyMap(String delimIt,String tab){
        this.delimIt=delimIt;
        this.tab=tab;
    }

    @Override
    public Map<String, Object> parse(String line) {
        String[] strs=line.split(delimIt);
        for(String str:strs){
            String[] kv=str.split(tab);
            if(kv.length==2){
                resultMap.put(kv[0],kv[1]);
            }
        }
        filter(resultMap);
        return resultMap;
    }

    public static void main(String[] args) {
        String[] strs="1,2,".split(",");
        System.out.println(strs.length);
        for (String str:strs){
            System.out.println(str);
        }
    }

}
