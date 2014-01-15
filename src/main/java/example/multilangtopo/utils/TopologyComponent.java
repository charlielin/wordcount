package example.multilangtopo.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-15
 * Time: 下午4:49
 */
public class TopologyComponent {
    private SpoutComponent[] spout;
    private BoltComponent[] bolt;

    public SpoutComponent[] getSpout() {
        return spout;
    }

    public void setSpout(SpoutComponent[] spout) {
        this.spout = spout;
    }

    public BoltComponent[] getBolt() {
        return bolt;
    }

    public void setBolt(BoltComponent[] bolt) {
        this.bolt = bolt;
    }
}
