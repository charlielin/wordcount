package example.multilangtopo.utils;

import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.IRichSpout;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-16
 * Time: 上午11:31
 */
public class ComponentFactory {

    private int curBoltIdx = 0;
    private int curSpoutIdx = 0;
    private static final int MAX_SPOUT_CLASS_NUM = 3;
    private static final int MAX_BOLT_CLASS_NUM = 20;
    private static Logger _LOG = Logger.getLogger(ComponentFactory.class);

    private String getBoltClassName() {
        return "example.multilangtopo.bolt.ShellBolt"+String.valueOf(curBoltIdx);
    }

    private String getSpoutClassName() {
        return "example.multilangtopo.spout.ShellSpout"+String.valueOf(curSpoutIdx);
    }

    private boolean IsAvailableBoltClassLeft() {
        return curBoltIdx < MAX_BOLT_CLASS_NUM;
    }

    private boolean IsAvailableSpoutClassLeft() {
        return curSpoutIdx < MAX_SPOUT_CLASS_NUM;
    }
    private void IncreaseCurBoltIdx() {
        curBoltIdx++;
    }
    private void IncreaseCurSpoutIdx() {
        curSpoutIdx++;
    }

    public IRichBolt createBoltObj(String lang, String langFile, String[] fields)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (IsAvailableBoltClassLeft()) {
            String className = getBoltClassName();
            IncreaseCurBoltIdx();
            Class clazz = Class.forName(className);
            Constructor constructor = clazz.getDeclaredConstructor(new Class[] {String.class, String.class, String[].class});
            constructor.setAccessible(true);
            return (IRichBolt)constructor.newInstance(new Object[]{lang, langFile, fields});
        } else {
            throw new ClassNotFoundException("No Available Bolt Class Left. You got "
                    +getCurBoltIdx()+" BoltClass. Max is " + MAX_BOLT_CLASS_NUM);
        }
    }

    public IRichSpout createSpoutObj(String lang, String langFile, String[] fields)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (IsAvailableSpoutClassLeft()) {
            String className = getSpoutClassName();
            IncreaseCurSpoutIdx();
            Class clazz = Class.forName(className);
            Constructor constructor = clazz.getDeclaredConstructor(new Class[] {String.class, String.class, String[].class});
            constructor.setAccessible(true);
            return (IRichSpout)constructor.newInstance(new Object[]{lang, langFile, fields});
        } else {
            throw new ClassNotFoundException("No Available Spout Class Left. You got "
            +getCurSpoutIdx()+" SpoutClass. Max is " + MAX_SPOUT_CLASS_NUM);
        }
    }

    public int getCurBoltIdx() {
        return curBoltIdx;
    }

    public int getCurSpoutIdx() {
        return curSpoutIdx;
    }
}
