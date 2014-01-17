package example.multilangtopo.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-17
 * Time: 下午2:08
 */
public class TopologyConf {
    private int localclustersleepmsecs;
    private int maxtaskparallelism;
    private int workersnum;
    private int ackersnum;
    private int maxspoutpending;
    private boolean debug;
    private int messagetimeoutsecs;

    public int getLocalclustersleepmsecs() {
        return localclustersleepmsecs;
    }

    public void setLocalclustersleepmsecs(int localclustersleepmsecs) {
        this.localclustersleepmsecs = localclustersleepmsecs;
    }

    public int getMaxtaskparallelism() {
        return maxtaskparallelism;
    }

    public void setMaxtaskparallelism(int maxtaskparallelism) {
        this.maxtaskparallelism = maxtaskparallelism;
    }

    public int getWorkersnum() {
        return workersnum;
    }

    public void setWorkersnum(int workersnum) {
        this.workersnum = workersnum;
    }

    public int getMaxspoutpending() {
        return maxspoutpending;
    }

    public void setMaxspoutpending(int maxspoutpending) {
        this.maxspoutpending = maxspoutpending;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getMessagetimeoutsecs() {
        return messagetimeoutsecs;
    }

    public void setMessagetimeoutsecs(int messagetimeoutsecs) {
        this.messagetimeoutsecs = messagetimeoutsecs;
    }

    public int getAckersnum() {
        return ackersnum;
    }

    public void setAckersnum(int ackersnum) {
        this.ackersnum = ackersnum;
    }
}
