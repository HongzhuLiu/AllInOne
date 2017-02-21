package parse.lexer;

import org.apache.commons.collections.map.HashedMap;
import parse.filter.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LHZ on 2016/10/26.
 */
public interface Lexer {
     Map<String,Object> parse(String line);
}
