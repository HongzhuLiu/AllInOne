package parse.lexer;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by LHZ on 2016/10/27.
 */
public class JSONLexer extends BaseLexer {
    @Override
    public Map<String, Object> parse(String line) {
        resultMap= (Map<String, Object>) JSON.parse(line);
        filter(resultMap);
        return resultMap;
    }
}
