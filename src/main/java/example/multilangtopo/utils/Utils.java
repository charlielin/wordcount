package example.multilangtopo.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-16
 * Time: 上午11:31
 */
public class Utils {
    public static Object createObject(String className)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class clazz = Class.forName(className);
        return clazz.newInstance();
    }
}
