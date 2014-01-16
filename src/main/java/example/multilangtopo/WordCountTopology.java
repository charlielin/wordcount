package example.multilangtopo;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.task.ShellBolt;
import backtype.storm.topology.*;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.multilangtopo.spout.RandomSentenceShellSpout;
import example.multilangtopo.bolt.SplitSentenceShellBolt;
import example.multilangtopo.bolt.WordCountShellBolt;

import example.multilangtopo.utils.*;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-14
 * Time: 上午11:11
 */
public class WordCountTopology {
    private static Logger _LOG = Logger.getLogger(WordCountTopology.class);
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

        List<BaseComponent[]> baseComponentsList = LoadTopologyStructure.getTopologyComponent();
        SpoutComponent[] spoutComponents = (SpoutComponent[]) baseComponentsList.get(0);
        BoltComponent[] boltComponents = (BoltComponent[]) baseComponentsList.get(1);
        for (SpoutComponent spoutComponent : spoutComponents) {
            _LOG.info("ComponentName: "+spoutComponent.getComponent_name());
            _LOG.info("ClassName: "+spoutComponent.getClass_name());
            _LOG.info("TaskNum: "+spoutComponent.getTask_num());
        }
        for (BoltComponent boltComponent : boltComponents) {
            _LOG.info("GroupingArgs: "+ Arrays.toString(boltComponent.getGrouping_args()));
        }
        TopologyBuilder builder = new TopologyBuilder();

        /*
        for (SpoutComponent spoutComponent : spoutComponents) {
            builder.setSpout(
                    spoutComponent.getComponent_name(),
                    (IRichSpout)Utils.createObject(spoutComponent.getClass_name()),
                    spoutComponent.getComponent_num()
                    )
                    .setNumTasks(spoutComponent.getTask_num());
        }*/
        builder.setSpout("spout", new RandomSentenceShellSpout(), 1/*BoltNum*/).setNumTasks(1);
        /*
        for (BoltComponent boltComponent : boltComponents) {
            builder.setBolt(
                    boltComponent.getComponent_name(),
                    (IRichBolt)Utils.createObject(boltComponent.getClass_name()),
                    boltComponent.getComponent_num()
                    )
                    .setNumTasks(boltComponent.getTask_num());
        }
        */
        builder.setBolt("split", new SplitSentenceShellBolt(), 1/*BoltNum*/).setNumTasks(1)
                .shuffleGrouping("spout");
        builder.setBolt("count", new WordCountShellBolt(), 1/*BoltNum*/).setNumTasks(1)
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

            Thread.sleep(20000);

            cluster.shutdown();
        }
    }
}
