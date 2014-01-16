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
 * Time: 下午2:20
 */
public class WordCountShellBolt extends ShellBolt implements IRichBolt {

    public WordCountShellBolt() {
        super("python", "wordcount.py");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "count"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
