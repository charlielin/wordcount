package example.multilangtopo.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-15
 * Time: 下午3:06
 */
public class BaseComponent {
    private String ComponentName;
    private String ClassName;
    private int ComponentNum;
    private int TaskNum;

    public String getComponentName() {
        return ComponentName;
    }

    public void setComponentName(String componentName) {
        this.ComponentName = componentName;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        this.ClassName = className;
    }

    public int getComponentNum() {
        return ComponentNum;
    }

    public void setComponentNum(int componentNum) {
        this.ComponentNum = componentNum;
    }

    public int getTaskNum() {
        return TaskNum;
    }

    public void setTaskNum(int taskNum) {
        this.TaskNum = taskNum;
    }
}
