package example.multilangtopo.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-15
 * Time: 下午3:06
 */
public class BaseComponent {
    private String component_name;
    private String[] output_fields;
    private String lang;
    private String file_name;
    private int component_num;
    private int task_num;

    public String getComponent_name() {
        return component_name;
    }

    public void setComponent_name(String component_name) {
        this.component_name = component_name;
    }

    public String[] getOutput_fields() {
        return output_fields;
    }

    public void setOutput_fields(String[] output_fields) {
        this.output_fields = output_fields;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getComponent_num() {
        return component_num;
    }

    public void setComponent_num(int component_num) {
        this.component_num = component_num;
    }

    public int getTask_num() {
        return task_num;
    }

    public void setTask_num(int task_num) {
        this.task_num = task_num;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
