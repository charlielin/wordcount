package example.multilangtopo.bolt;

import backtype.storm.task.ShellBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-24
 * Time: 下午2:36
 */
public class ShellBolt3 extends ShellBolt implements IRichBolt {

    private String[] _fields;

//    public ShellBolt1() {
//        super("python", "splitsentence.py");
//    }

    public ShellBolt3(String lang, String langFile, String[] fields) {
        super(lang, langFile);
        _fields = fields;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(_fields));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
