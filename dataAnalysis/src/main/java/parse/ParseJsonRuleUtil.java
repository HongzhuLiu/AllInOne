package parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import common.util.ParseUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import parse.filter.*;
import parse.lexer.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by LHZ on 2016/10/26.
 */
public class ParseJsonRuleUtil {
    private ParseJsonRuleUtil(){

    }

    public static BaseLexer parseRule(String json){
        Map ruleMap= (Map) JSON.parse(json);
        String sample=ruleMap.get("sample")+"";
        Map parserMap= (Map) ruleMap.get("parser");
        String name = parserMap.get("name")+"";
        String value = parserMap.get("value")+"";
        String delimit=parserMap.get("delimit")+"";
        BaseLexer lexer=null;
        switch (name) {
            case "regex":
                lexer=new RegexLexer(value);
                break;
            case "json":
                lexer=new JSONLexer();
                break;
            case "xml":
                lexer=new XMLLexer();
                break;
            case "delimit":
                JSONArray fields = (JSONArray) parserMap.get("fields");
                String[] strs=new String[fields.size()];
                for(int i=0;i<fields.size();i++){
                    strs[i]=((Map<String,String>) fields.get(i)).get("value");
                }
                if(strs.length>0&&StringUtils.isNotEmpty(delimit)){
                    lexer=new DelimiterLexer(strs,delimit);
                }else{
                    throw new RuntimeException("DelimiterLexer need fields and delimit");
                }
                break;
            case "delimitWithKeyMap":
                String tab=parserMap.get("tab")+"";
                if(StringUtils.isNotEmpty(delimit)&&StringUtils.isNotEmpty(tab)){
                    lexer=new DelimitWithKeyMap(delimit,tab);
                }else{
                    throw new RuntimeException("delimitWithKeyMap need tab and delimit");
                }
                break;
            case "select":
                JSONArray parsers = (JSONArray) parserMap.get("parsers");
                ArrayList<Lexer> lexers=new ArrayList<>();
                for (int i=0;i<parsers.size();i++){
                    String ruleIdStr=parsers.get(i)+"";
                    if(StringUtils.isNotEmpty(ruleIdStr)){
                        int ruleId=Integer.parseInt(ruleIdStr);
                        String ruleJson= ParseUtil.ruleMap.get(ruleId);
                        if(ruleJson!=null){
                            Lexer tlexer=parseRule(ruleJson);
                            if(tlexer!=null){
                                lexers.add(tlexer);
                            }
                        }else{
                            throw new RuntimeException("ruleId:"+ruleId+" not exist");
                        }
                    }
                }
                if(lexers.size()>0){
                    lexer=new SelectLexer(lexers);
                }else{
                    throw new RuntimeException("SelectLexer need parsers");
                }
                break;
            default:
                lexer=new BaseLexer();
                break;
        }
        if(lexer!=null){
            lexer.setSample(sample);
            //过滤器
            JSONArray filters = (JSONArray) parserMap.get("filter");
            for (int i = 0; i < filters.size(); i++) {
                Map filterMap = (Map) filters.get(i);
                Filter filter=paserFilter(filterMap);
                if(filter!=null){
                    lexer.setFilter(filter);
                }
            }
        }else{
            throw new RuntimeException("lexer is null");
        }
        return lexer;
    }

    //解析出过滤器
    private static Filter paserFilter(Map<String, Object> filterMap){
        Filter filter=null;
        String actionName = filterMap.get("name")+"";
        JSONArray fields = (JSONArray) filterMap.get("fields");
        String field = filterMap.get("field")+"";
        JSONArray cases = (JSONArray) filterMap.get("cases");

        Map<String,String> keyValueMap=new HashedMap();
        ArrayList<Case> caseList=new ArrayList<>();
        switch (actionName) {
            case "addFields":
                for (int i = 0; i < fields.size(); i++) {
                    Map map = (Map) fields.get(i);
                    String key = map.get("key")+"";
                    String value = map.get("value")+"";
                    //TODO
                    value=cutFieldName(value);
                    if(StringUtils.isNotEmpty(key)&&StringUtils.isNotEmpty(value)){
                        keyValueMap.put(key,value);
                    }
                }
                if(!keyValueMap.isEmpty()){
                    filter=new AddFields(keyValueMap);
                }
                 break;
            case "removeFields":
                Set<String> set=new HashSet<>();
                for (int i = 0; i < fields.size(); i++) {
                    Map map = (Map) fields.get(i);
                    String value = map.get("value")+"";
                    if(StringUtils.isNotEmpty(value)){
                        set.add(value);
                    }
                }
                if(set.size()>0){
                    filter=new RemoveFields(set);
                }
                break;
            case "mapping":
                for (int i = 0; i < fields.size(); i++) {
                    Map map = (Map) fields.get(i);
                    String key = map.get("value")+"";
                    String value = map.get("key")+"";
                    if(StringUtils.isNotEmpty(key)&&StringUtils.isNotEmpty(value)){
                        keyValueMap.put(key,value);
                    }
                }
                if(!keyValueMap.isEmpty()){
                    filter=new RenameFields(keyValueMap);
                }
                break;
            case "merger":
                ArrayList<String> list=new ArrayList<>();
                for (int i = 0; i < fields.size(); i++) {
                    Map map = (Map) fields.get(i);
                    String value = map.get("value")+"";
                    if(StringUtils.isNotEmpty(value)){
                        list.add(value);
                    }
                }
                if(list.size()>0&&StringUtils.isNotEmpty(field)){
                    String sep = filterMap.get("sep")+"";
                    filter=new MergeFields(list,sep,field);
                }
                break;
            case "fieldCut":
                String fromStr = filterMap.get("from")+"";
                String toStr = filterMap.get("to")+"";
                if(StringUtils.isNotEmpty(fromStr)&&StringUtils.isNotEmpty(toStr)){
                    int from=Integer.parseInt(fromStr);
                    int limit=Integer.parseInt(toStr);
                    if(from>=0&&limit>0){
                        filter=new CutFields(field,from,limit);
                    }
                }
                break;
            case "startWithFields":
                for (int i = 0; i < cases.size(); i++) {
                    Map caseMap= (Map) cases.get(i);
                    String value= caseMap.get("value")+"";
                    Map ruleMap= (Map) caseMap.get("rule");
                    Filter tfilter=paserFilter(ruleMap);
                    if(tfilter!=null){
                        Case caseObj=new Case(value,tfilter);
                        caseList.add(caseObj);
                    }
                }
                if(caseList.size()>0&&StringUtils.isNotEmpty(field)){
                    filter=new StartWithFields(field,caseList);
                }
                break;
            case "endWithFields":
                for (int i = 0; i < cases.size(); i++) {
                    Map caseMap= (Map) cases.get(i);
                    String value= caseMap.get("value")+"";
                    Map ruleMap= (Map) caseMap.get("rule");
                    Filter tfilter=paserFilter(ruleMap);
                    if(tfilter!=null){
                        Case caseObj=new Case(value,tfilter);
                        caseList.add(caseObj);
                    }
                }
                if(caseList.size()>0&&StringUtils.isNotEmpty(field)){
                    filter=new EndWithFields(field,caseList);
                }
                break;
            case "contain":
                for (int i = 0; i < cases.size(); i++) {
                    Map caseMap= (Map) cases.get(i);
                    String value= caseMap.get("value")+"";
                    Map ruleMap= (Map) caseMap.get("rule");
                    Filter tfilter=paserFilter(ruleMap);
                    if(tfilter!=null){
                        Case caseObj=new Case(value,tfilter);
                        caseList.add(caseObj);
                    }
                }
                if(caseList.size()>0&&StringUtils.isNotEmpty(field)){
                    filter=new ContainFields(field,caseList);
                }
                break;
            case "match":
                for (int i = 0; i < cases.size(); i++) {
                    Map caseMap= (Map) cases.get(i);
                    String value= caseMap.get("value")+"";
                    Map ruleMap= (Map) caseMap.get("rule");
                    Filter tfilter=paserFilter(ruleMap);
                    if(tfilter!=null){
                        Case caseObj=new Case(value,tfilter);
                        caseList.add(caseObj);
                    }
                }
                if(caseList.size()>0&&StringUtils.isNotEmpty(field)){
                    filter=new RegexFields(field,caseList);
                }
                break;
            case "analyzer":
                String refStr= filterMap.get("ref")+"";
                if(StringUtils.isNotEmpty(refStr)){
                    int ruleId=Integer.parseInt(refStr);
                    String ruleJson= ParseUtil.ruleMap.get(ruleId);
                    if(ruleJson!=null){
                        Lexer tlexer=parseRule(ruleJson);
                        if(tlexer==null){
                            filter=new AnalyzerFields(field,tlexer);
                        }
                    }else{
                        throw new RuntimeException("ruleId:"+ruleId+" not exist");
                    }
                }
                break;
            default:
                break;
        }
        return filter;
    }

    //获取真实的字段名
    public static String cutFieldName(String value){
        if(StringUtils.isNotEmpty(value)&&(value.length()-1)>2){
            return value.substring(2,value.length()-1);
        }else{
            throw new RuntimeException("field "+value+" format error!");
        }
    }

}
