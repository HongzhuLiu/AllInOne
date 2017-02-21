package parse.lexer;

import org.apache.commons.collections.map.HashedMap;
import parse.filter.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LHZ on 2016/10/27.
 */
public class BaseLexer implements Lexer{
    private String sample;
    protected Map<String, Object> resultMap =new HashedMap();
    protected List<Filter> filterList=new ArrayList<>();

    @Override
    public Map<String,Object> parse(String line){
        resultMap.put("content",line);
        return resultMap;
    }

    public void setFilter(Filter filter) {
        filterList.add(filter);
    }


    public void filter(Map<String, Object> resultMap){
        for (Filter filter:filterList){
            filter.filter(resultMap);
        }
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }
}
