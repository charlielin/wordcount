package example.multilangtopo.bolt;

import backtype.storm.task.ShellBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-14
 * Time: 上午11:20
 */
public class SplitSentenceShellBolt extends ShellBolt implements IRichBolt {

    public SplitSentenceShellBolt() {
        super("python", "splitsentence.py");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }





    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
