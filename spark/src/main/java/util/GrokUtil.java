package util;

import oi.thekraken.grok.api.Grok;
import oi.thekraken.grok.api.Match;
import oi.thekraken.grok.api.exception.GrokException;

import java.io.File;

/**
 * Created by lhz on 16-8-23.
 */
public class GrokUtil {
    private  static Grok grok=new Grok();
    public static void main(String[] args) throws GrokException {
        Grok grok = Grok.create("patterns/patterns");
        grok.compile("%{COMBINEDAPACHELOG}");
        String log =
                "112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22\"";
        Match gm = grok.match(log);
        gm.captures();
        System.out.println(gm.toJson());
    }

    public static void loadPattern(File file){
        try {
            if(file!=null&&file.exists()){
                if(file.isDirectory()){
                    File[] files=file.listFiles();
                    for(File tfile:files){
                        loadPattern(tfile);
                    }
                }else {
                    grok.addPatternFromFile(file.getAbsolutePath());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
