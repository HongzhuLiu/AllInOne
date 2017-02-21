package parse.lexer;

import parse.grok.CustomGrok;
import oi.thekraken.grok.api.Match;
import oi.thekraken.grok.api.exception.GrokException;

import java.util.Map;

/**
 * Created by LHZ on 2016/10/26.
 */
public class RegexLexer extends BaseLexer {
    private CustomGrok grok;

    public RegexLexer(String pattern){
        this("F:\\Git\\AllInOne\\dataAnalysis\\conf\\patterns",pattern);
    }

    public RegexLexer(String file,String pattern){
        try {
            this.grok=new CustomGrok();
            grok.addPatternFromFile(file);
            grok.compile(pattern);
        } catch (GrokException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> parse(String line) {
        Match gm = grok.match(line);
        gm.captures();
        resultMap.putAll(gm.toMap());
        filter(resultMap);
        return resultMap;
    }
}
