package example.multilangtopo.spout;

import backtype.storm.spout.ISpout;
import backtype.storm.spout.ShellSpout;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-14
 * Time: 下午4:48
 */
public class RandomSentenceShellSpout extends ShellSpout implements IRichSpout{

    public RandomSentenceShellSpout() {
        super("python", "randomsentence.py");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
