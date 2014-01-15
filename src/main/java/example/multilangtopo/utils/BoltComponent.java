package example.multilangtopo.utils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-15
 * Time: 下午3:13
 */
public class BoltComponent extends BaseComponent {
    private String Grouping;
    private String[] GroupingArgs;
    private String FromComponent;

    public String getGrouping() {
        return Grouping;
    }

    public void setGrouping(String grouping) {
        this.Grouping = grouping;
    }


    public String getFromComponent() {
        return FromComponent;
    }

    public void setFromComponent(String fromComponent) {
        this.FromComponent = fromComponent;
    }

    public String[] getGroupingArgs() {
        return GroupingArgs;
    }

    public void setGroupingArgs(String[] groupingArgs) {
        GroupingArgs = groupingArgs;
    }
}
