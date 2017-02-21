package parse.filter;

import com.google.code.regexp.Matcher;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by LHZ on 2016/10/26.
 */
public interface Filter {
    void filter(Map<String, Object> resultMap);
}



