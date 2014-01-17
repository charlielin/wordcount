package example.multilangtopo;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.*;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.lang.reflect.Method;
import java.util.*;

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


        for (SpoutComponent spoutComponent : spoutComponents) {
            builder.setSpout(
                    spoutComponent.getComponent_name(),
                    ComponentFactory.createSpoutObj(spoutComponent.getClass_name()),
                    spoutComponent.getComponent_num()
                    )
                    .setNumTasks(spoutComponent.getTask_num());
        }
        //builder.setSpout("spout", new RandomSentenceShellSpout(), 1/*BoltNum*/).setNumTasks(1);

        for (BoltComponent boltComponent : boltComponents) {
            Class invokeBoltDeclarerClass = Class.forName("backtype.storm.topology.BoltDeclarer");
            String grouping = boltComponent.getGrouping();
            String fromComponent = boltComponent.getFrom_component();
            String[] groupingArgs = boltComponent.getGrouping_args();

            BoltDeclarer boltDeclarer = builder.setBolt(
                    boltComponent.getComponent_name(),
                    ComponentFactory.createBoltObj(boltComponent.getClass_name()),
                    boltComponent.getComponent_num()
            );
            _LOG.info("builder.setBolt("+boltComponent.getComponent_name()+", "+
                    ComponentFactory.createBoltObj(boltComponent.getClass_name())+", "+
                    boltComponent.getComponent_num()+")");
            if (grouping.equals("fieldsGrouping")) {
                Class paras[] = {String.class, Fields.class};
                Method method = invokeBoltDeclarerClass.getMethod(grouping, paras);

                List<String> fieldsList = Arrays.asList(groupingArgs);
                Fields fields = new Fields(fieldsList);
                method.invoke(boltDeclarer, new Object[] { fromComponent, fields });
                _LOG.info("boltDeclarer invoke: "+fromComponent+", "+fieldsList.toString());

            } else if (grouping.equals("globalGrouping")|| grouping.equals("shuffleGrouping")
                    || grouping.equals("localOrShuffleGrouping") || grouping.equals("noneGrouping")
                    || grouping.equals("allGrouping") || grouping.equals("directGrouping")) {
                Class paras[] = {String.class};
                Method method = invokeBoltDeclarerClass.getMethod(grouping, paras);

                method.invoke(boltDeclarer, new Object[] { fromComponent });
                _LOG.info("boltDeclarer invoke: "+fromComponent);
            }
        }

//        builder.setBolt("split", new SplitSentenceShellBolt(), 1/*BoltNum*/).setNumTasks(1)
//                .shuffleGrouping("spout");
//       builder.setBolt("count", new WordCountShellBolt(), 1/*BoltNum*/).setNumTasks(1)
//                .fieldsGrouping("split", new Fields("word"));


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
