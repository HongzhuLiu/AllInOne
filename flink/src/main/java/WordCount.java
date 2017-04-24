import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * Created by LHZ on 2017/3/23.
 */
public class WordCount {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env=ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> text=env.readTextFile("F:\\IDEA_WORKSPACE\\AllInOne\\flink\\src\\main\\resources\\word.txt");
        DataSet<Tuple2<String, Integer>> counts=text.flatMap(new Tokenizer())
                .groupBy(0)
                .sum(1);
        counts.print();
    }


    public static class Tokenizer implements FlatMapFunction<String,Tuple2<String,Integer>>{
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
            String[] tokens=value.toLowerCase().split("\\s+");
            for(String token:tokens){
                if(token.length()>0){
                    out.collect(new Tuple2<>(token,1));
                }
            }
        }
    }
}
