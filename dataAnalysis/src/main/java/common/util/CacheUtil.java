package common.util;

import com.google.common.cache.*;
import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by LHZ on 2016/10/21.
 */
public class CacheUtil {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<String,String> cahceBuilder=CacheBuilder
                .newBuilder()
                .refreshAfterWrite(1,TimeUnit.MINUTES)
                .initialCapacity(10)
                .maximumSize(100)
                .build(new CacheLoader<String, String>(){
                    @Override
                    public String load(String key) throws Exception {
                        String strProValue="hello "+key+"!";
                        return strProValue;
                    }

                    @Override
                    public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                        System.out.println("key:"+key+"\t"+"oldValue:"+oldValue);
                        return super.reload(key, oldValue);
                    }

                });
        System.out.println("jerry value:"+cahceBuilder.get("jerry"));
        System.out.println("jerry value:"+cahceBuilder.get("jerry"));
        cahceBuilder.put("harry", "ssdded");
        System.out.println("harry value:"+cahceBuilder.get("harry"));
        TimeUnit.SECONDS.sleep(10);
        System.out.println("harry value:"+cahceBuilder.get("harry"));
        TimeUnit.SECONDS.sleep(10);
    }
}
