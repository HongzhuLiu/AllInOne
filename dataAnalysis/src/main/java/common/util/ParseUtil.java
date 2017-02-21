package common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;
import parse.ParseJsonRuleUtil;
import parse.lexer.BaseLexer;
import parse.lexer.Lexer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by LHZ on 2016/10/27.
 */
public class ParseUtil {
    public static Map<Integer,String> ruleMap=new HashedMap();
    static {
        File file=new File("F:\\Git\\AllInOne\\dataAnalysis\\conf\\rules");
        if(file.exists()){
            int i=1;
            if(file.isFile()){
                loadRules(i++,file);
            }else{
                File[] files=file.listFiles();
                for(File tfile:files){
                    loadRules(i++,tfile);
                }
            }
        }
    }

    public static Lexer getLexer(String type){
        BaseLexer lexer= ParseJsonRuleUtil.parseRule(ruleMap.get(6));
        return lexer;
    }

    public static void main(String[] args){
        BaseLexer lexer= ParseJsonRuleUtil.parseRule(ruleMap.get(2));
        String sample= lexer.getSample();
        while(true){
            long start=System.currentTimeMillis();
            for(int i=0;i<10000;i++){
                Map<String,Object> map=lexer.parse(sample);
                System.out.println(JSON.toJSON(map));
            }
            long end=System.currentTimeMillis();
            System.out.println(((end-start)/1000)+"s");
            //System.out.println(resultMap);
        }
    }

    //加载解析规则
    private static void loadRules(int id,File file){
        try {
            List<String> list= Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
            StringBuffer sb=new StringBuffer();
            for(String str:list){
                sb.append(str);
            }
            ruleMap.put(id,sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
