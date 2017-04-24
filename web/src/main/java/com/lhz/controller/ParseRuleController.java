package com.lhz.controller;

import com.lhz.mapper.ParseRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LHZ on 2017/4/7.
 */
@RestController
@RequestMapping("/api/parseRule")
public class ParseRuleController {
    private Pattern pattern;
    @Autowired
    ParseRuleMapper parseRuleMapper;
    @RequestMapping(value = "/regex")
    public ResponseEntity parse(){
        List<HashMap<String,Object>> list=parseRuleMapper.findList(new HashMap<String,Object>());
        int count=0;
        for(int i=0;i<list.size();i++){
            long startTime=System.currentTimeMillis();
            HashMap<String,Object> resultMap=list.get(i);
            String name=resultMap.get("parser_name")+"";
            String regex=resultMap.get("regex")+"";
            Pattern pattern = Pattern.compile(regex);
            for(int k=0;k<list.size();k++){
                HashMap<String,Object> tresultMap=list.get(k);
                String content=tresultMap.get("org_event_content")+"";
                Matcher matcher = pattern.matcher(content.trim());
                if (matcher.find()) {
                    System.out.println("正确解析");
                }
            }
            count++;
            long endTime=System.currentTimeMillis();
            System.out.println(name+"-解析耗时,"+(endTime-startTime)+",ms");
        }

        System.out.println("count==="+count);
        return ResponseEntity.ok(list);
    }
}
