package example.multilangtopo.utils;

import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.IRichSpout;
import example.multilangtopo.bolt.*;
import example.multilangtopo.spout.RandomSentenceShellSpout;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-16
 * Time: 上午11:31
 */
public class ComponentFactory {
    public static IRichSpout createSpoutObj(String className)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class clazz = Class.forName(className);
        return (IRichSpout)clazz.newInstance();
    }

    public static IRichBolt createBoltObj(String className)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class clazz = Class.forName(className);
        return (IRichBolt)clazz.newInstance();
    }
}
