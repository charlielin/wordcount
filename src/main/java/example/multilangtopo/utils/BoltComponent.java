package example.multilangtopo.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-15
 * Time: 下午3:13
 */
public class BoltComponent extends BaseComponent {
    private String grouping;
    private String[] grouping_args;
    private String from_component;

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }


    public String getFrom_component() {
        return from_component;
    }

    public void setFrom_component(String from_component) {
        this.from_component = from_component;
    }

    public String[] getGrouping_args() {
        return grouping_args;
    }

    public void setGrouping_args(String[] grouping_args) {
        this.grouping_args = grouping_args;
    }
}
