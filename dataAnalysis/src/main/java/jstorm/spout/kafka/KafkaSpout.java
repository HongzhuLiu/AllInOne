package jstorm.spout.kafka;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import common.model.Event;
import common.model.EventBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class KafkaSpout implements IRichSpout {
	private static Logger LOG = LoggerFactory.getLogger(KafkaSpout.class);
	private SpoutOutputCollector collector;
	private KafkaConsumer<Integer, byte[]> consumer;
	public KafkaSpout() {

	}
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector=collector;
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.18.123.190:9092,172.18.123.191:9092,172.18.123.192:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "stormSpout");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Collections.singletonList("saas"));
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextTuple() {
		ConsumerRecords<Integer, byte[]> records = consumer.poll(1000);
		for (ConsumerRecord<Integer, byte[]> record : records) {
			Event e = EventBuilder.buildEvent(record.value());
			if(e!=null){
				collector.emit(new Values(e));
			}
		}
	}
	


	@Override
	public void ack(Object msgId) {
	}

	@Override
	public void fail(Object msgId) {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("bytes"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
	

}
