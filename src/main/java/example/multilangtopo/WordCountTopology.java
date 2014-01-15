package example.multilangtopo;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.task.ShellBolt;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

import example.multilangtopo.spout.RandomSentenceShellSpout;
import example.multilangtopo.bolt.SplitSentenceShellBolt;
import example.multilangtopo.bolt.WordCountShellBolt;

import example.multilangtopo.utils.LoadTopologyStructure;
import example.multilangtopo.utils.SpoutComponent;
/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-14
 * Time: 上午11:11
 */
public class WordCountTopology {
    public static class WordCount extends BaseBasicBolt {
        Map<String, Integer> counts = new HashMap<String, Integer>();

        @Override
        public void execute(Tuple tuple, BasicOutputCollector collector) {
            String word = tuple.getString(0);
            Integer count = counts.get(word);
            if(count==null) count = 0;
            count++;
            counts.put(word, count);
            collector.emit(new Values(word, count));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }
    }

    public static void main(String[] args) throws Exception {

        LoadTopologyStructure.getTopologyComponent();
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new RandomSentenceShellSpout(), 5/*BoltNum*/).setNumTasks(5);
        builder.setBolt("split", new SplitSentenceShellBolt(), 8/*BoltNum*/).setNumTasks(5)
                .shuffleGrouping("spout");
        builder.setBolt("count", new WordCountShellBolt(), 12/*BoltNum*/).setNumTasks(12)
                .fieldsGrouping("split", new Fields("word"));



        Config conf = new Config();
        conf.setDebug(true);


        if(args!=null && args.length > 0) {
            conf.setNumWorkers(3);

            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(3);

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());

            Thread.sleep(10000);

            cluster.shutdown();
        }
    }
}
