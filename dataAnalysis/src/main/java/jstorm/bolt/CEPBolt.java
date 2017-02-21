package jstorm.bolt;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.espertech.esper.client.*;
import jstorm.model.LogBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by LHZ on 2016/9/30.
 */
public class CEPBolt extends BaseBasicBolt {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private EPServiceProvider epService;
    private BasicOutputCollector outputCollector;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        Configuration config = new Configuration();
        config.addEventType(LogBean.class);
        this.epService = EPServiceProviderManager.getDefaultProvider(config);
        String epl = "select count(*) from LogBean.win:length_batch(3)";
        EPStatement statement = epService.getEPAdministrator().createEPL(epl);
        UpdateListener listener = new UpdateListener() {
            @Override
            public void update(EventBean[] newEvents, EventBean[] oldEvents) {
                EventBean event = newEvents[0];
                //outputCollector.equals(event);
                log.info("................total num at last 10s:{}",event.get("count(*)"));
            }
        };
        statement.addListener(listener);
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        this.outputCollector=collector;
        LogBean logBean= (LogBean) input.getValue(0);
        epService.getEPRuntime().sendEvent(logBean);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public void cleanup() {
        if(epService!=null){
            epService.destroy();
        }
    }
}