package parse.filter;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;

/**
 * Created by LHZ on 2016/10/26.
 */
public class Case {
    private String caseStr;
    private Filter filter;
    private Matcher matcher;

    public Case(String caseStr,Filter filter){
        this.caseStr=caseStr;
        this.filter=filter;
    }

    public String getCaseStr() {
        return caseStr;
    }

    public void setCaseStr(String caseStr) {
        this.caseStr = caseStr;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Matcher getMatcher(String caseStr) {
        if(matcher==null){
            Pattern pattern = Pattern.compile(caseStr);
            this.matcher = pattern.matcher(caseStr);
        }
        return matcher;
    }
}
