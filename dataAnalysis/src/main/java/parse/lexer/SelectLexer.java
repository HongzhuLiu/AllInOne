package parse.lexer;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by LHZ on 2016/10/27.
 */
public class SelectLexer extends BaseLexer {
    private ArrayList<Lexer> lexers;
    public SelectLexer(ArrayList<Lexer> lexers){
        this.lexers=lexers;
    }
    @Override
    public Map<String, Object> parse(String line) {
        for(Lexer lexer:lexers){
            resultMap=lexer.parse(line);
            if(resultMap!=null){
                return resultMap;
            }
        }
        return resultMap;
    }
}
