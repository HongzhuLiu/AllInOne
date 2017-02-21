package jstorm.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import jstorm.bolt.CEPBolt;
import jstorm.bolt.ParseBolt;
import jstorm.spout.kafka.KafkaSpout;

/**
 * Created by LHZ on 2016/9/30.
 */
public class CEPTopology {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafkaSpout",new KafkaSpout(),1);
        builder.setBolt("parseBolt",new ParseBolt(),1).fieldsGrouping("kafkaSpout",new Fields("bytes"));
        builder.setBolt("cepBolt",new CEPBolt(),1).fieldsGrouping("parseBolt",new Fields("map"));
        Config config = new Config();
        config.setNumWorkers(2);
        String name = CEPTopology.class.getSimpleName();

        if (args != null && args.length > 0) {
            // Nimbus host name passed from command line
            config.put(Config.NIMBUS_HOST, args[0]);
            StormSubmitter.submitTopologyWithProgressBar(name, config, builder.createTopology());
        } else {
            // 这里是本地模式下运行的启动代码。
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(name, config, builder.createTopology());
        }
    }
}
