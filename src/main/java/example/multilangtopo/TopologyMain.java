package example.multilangtopo;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.*;
import backtype.storm.tuple.Fields;

import java.lang.reflect.Method;
import java.util.*;

import example.multilangtopo.utils.*;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-14
 * Time: 上午11:11
 */
public class TopologyMain {
    private static Logger _LOG = Logger.getLogger(TopologyMain.class);


    public static void main(String[] args) throws Exception {
        ComponentFactory componentFactory = new ComponentFactory();

        List<BaseComponent[]> baseComponentsList = LoadTopologyStructure.getTopologyComponent();
        SpoutComponent[] spoutComponents = (SpoutComponent[]) baseComponentsList.get(0);
        BoltComponent[] boltComponents = (BoltComponent[]) baseComponentsList.get(1);

        for (SpoutComponent spoutComponent : spoutComponents) {
            _LOG.info("ComponentName: "+spoutComponent.getComponent_name());
            _LOG.info("output_fields: "+Arrays.toString(spoutComponent.getOutput_fields()));
            _LOG.info("lang: "+spoutComponent.getLang());
            _LOG.info("file_name: "+spoutComponent.getFile_name());
            _LOG.info("task_num: "+ spoutComponent.getTask_num());
            _LOG.info("TaskNum: "+spoutComponent.getTask_num());
        }
        for (BoltComponent boltComponent : boltComponents) {
            _LOG.info("ComponentName: "+boltComponent.getComponent_name());
            _LOG.info("output_fields: "+Arrays.toString(boltComponent.getOutput_fields()));
            _LOG.info("lang: "+boltComponent.getLang());
            _LOG.info("file_name: "+boltComponent.getFile_name());
            _LOG.info("task_num: "+ boltComponent.getTask_num());
            _LOG.info("TaskNum: "+boltComponent.getTask_num());
            _LOG.info("grouping: "+boltComponent.getGrouping());
            _LOG.info("from_component: "+boltComponent.getFrom_component());
            _LOG.info("GroupingArgs: "+ Arrays.toString(boltComponent.getGrouping_args()));
        }
        TopologyBuilder builder = new TopologyBuilder();

        for (SpoutComponent spoutComponent : spoutComponents) {
            String lang = spoutComponent.getLang();
            String fileName = spoutComponent.getFile_name();
            String[] outputFields = spoutComponent.getOutput_fields();
            builder.setSpout(
                    spoutComponent.getComponent_name(),
                    componentFactory.createSpoutObj(lang, fileName, outputFields),
                    spoutComponent.getComponent_num()
                    )
                    .setNumTasks(spoutComponent.getTask_num());
        }

        for (BoltComponent boltComponent : boltComponents) {
            Class invokeBoltDeclarerClass = Class.forName("backtype.storm.topology.BoltDeclarer");
            String lang = boltComponent.getLang();
            String fileName = boltComponent.getFile_name();
            String[] outputFields = boltComponent.getOutput_fields();
            String grouping = boltComponent.getGrouping();
            String fromComponent = boltComponent.getFrom_component();
            String[] groupingArgs = boltComponent.getGrouping_args();
            //List<String> outputFieldsList = Arrays.asList(outputFields);
            BoltDeclarer boltDeclarer = builder.setBolt(
                    boltComponent.getComponent_name(),
                    componentFactory.createBoltObj(lang, fileName, outputFields),
                    boltComponent.getComponent_num()
            );
            _LOG.info("builder.setBolt("+boltComponent.getComponent_name()+", "+
                    componentFactory.createBoltObj(lang, fileName, outputFields)+", "+
                    boltComponent.getComponent_num()+")");
            if (grouping.equals("fieldsGrouping")) {
                Class paras[] = {String.class, Fields.class};
                Method method = invokeBoltDeclarerClass.getMethod(grouping, paras);

                //List<String> groupingArgsList = Arrays.asList(groupingArgs);
                Fields fields = new Fields(groupingArgs);
                method.invoke(boltDeclarer, new Object[] { fromComponent, fields });
                _LOG.info("boltDeclarer invoke: " + fromComponent + ", " + Arrays.toString(groupingArgs));

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
            cluster.submitTopology("multilangFrameWork", conf, builder.createTopology());

            Thread.sleep(topologyConf.getLocalclustersleepmsecs());

            cluster.shutdown();
        }
    }
}
