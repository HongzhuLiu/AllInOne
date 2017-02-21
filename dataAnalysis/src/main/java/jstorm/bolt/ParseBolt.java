package jstorm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import common.model.Event;
import common.util.ParseUtil;
import jstorm.model.LogBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.lexer.Lexer;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by LHZ on 2016/10/20.
 */
public class ParseBolt extends BaseBasicBolt {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Event event=(Event)input.getValue(0);
        String type=event.getHeaders().get("type");
        String line=new String(event.getBody(), Charset.forName("UTF-8"));
        Lexer lexer=ParseUtil.getLexer(type);
        Map<String,Object> map=lexer.parse(line);
        LogBean logBean=new LogBean();
        logBean.setLine(line);
        collector.emit(new Values(logBean));
        log.info("map:{}",map);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("map"));
    }
}
