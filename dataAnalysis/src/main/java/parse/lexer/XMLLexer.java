package parse.lexer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;
import java.util.Map;

/**
 * Created by LHZ on 2016/10/27.
 */
public class XMLLexer extends BaseLexer {
    @Override
    public Map<String, Object> parse(String line) {
        try {
            Document doc = DocumentHelper.parseText(line);
            Element rootElement = doc.getRootElement();
            String name=rootElement.getName();
            xmlToMap(rootElement,name,resultMap);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        filter(resultMap);
        return resultMap;
    }

    private void xmlToMap(Element element,String name,Map<String, Object> resultMap){
        List<Element> elements = element.elements();
        if(elements.size()==0){
            resultMap.put(name,element.getTextTrim());
        }else{
            for(Element telement:elements){
                String tname=name+"."+telement.getName();
                xmlToMap(telement,tname,resultMap);
            }
        }
    }
}
