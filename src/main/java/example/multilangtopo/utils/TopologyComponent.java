package example.multilangtopo.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-15
 * Time: 下午4:49
 */
public class TopologyComponent {
    private SpoutComponent[] Spout;
    private BoltComponent[] Bolt;

    public SpoutComponent[] getSpout() {
        return Spout;
    }

    public void setSpout(SpoutComponent[] spout) {
        this.Spout = spout;
    }

    public BoltComponent[] getBolt() {
        return Bolt;
    }

    public void setBolt(BoltComponent[] bolt) {
        this.Bolt = bolt;
    }
}
