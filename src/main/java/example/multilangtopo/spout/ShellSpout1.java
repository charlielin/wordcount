package example.multilangtopo.spout;

import backtype.storm.spout.ShellSpout;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-24
 * Time: 下午3:15
 */
public class ShellSpout1 extends ShellSpout implements IRichSpout {

    private String[] _fields;
//    public ShellSpout0() {
//        super("python", "randomsentence.py");
//    }

    public ShellSpout1(String lang, String langFile, String[] fields) {
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
