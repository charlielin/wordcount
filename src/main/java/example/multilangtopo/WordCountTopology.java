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
            } else {
                _LOG.error("grouping ERROR: "+grouping+"\nShould be fieldsGrouping/globalGrouping/shuffleGrouping" +
                        "/localOrShuffleGrouping/noneGrouping/allGrouping/directGrouping");
            }
        }

//        builder.setBolt("split", new SplitSentenceShellBolt(), 1/*BoltNum*/).setNumTasks(1)
//                .shuffleGrouping("spout");
//       builder.setBolt("count", new WordCountShellBolt(), 1/*BoltNum*/).setNumTasks(1)
//                .fieldsGrouping("split", new Fields("word"));


        TopologyConf topologyConf = LoadTopologyConf.getTopoConf();
        Config conf = new Config();
        conf.setDebug(topologyConf.isDebug());
        if (topologyConf.getMaxspoutpending() != 0) {
            conf.setMaxSpoutPending(topologyConf.getMaxspoutpending());
        }
        if (topologyConf.getMaxtaskparallelism() != 0) {
            conf.setMaxTaskParallelism(topologyConf.getMaxtaskparallelism());
        }

        if(args!=null && args.length > 0) {
            if (topologyConf.getWorkersnum() != 0) {
                conf.setNumWorkers(topologyConf.getWorkersnum());
            }
            if (topologyConf.getAckersnum() != 0) {
                conf.setNumAckers(topologyConf.getAckersnum());
            }
            if (topologyConf.getMessagetimeoutsecs() != 0) {
                conf.setMessageTimeoutSecs(topologyConf.getMessagetimeoutsecs());
            }
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());

            Thread.sleep(topologyConf.getLocalclustersleepmsecs());

            cluster.shutdown();
        }
    }
}
