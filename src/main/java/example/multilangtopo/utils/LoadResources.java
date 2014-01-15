package example.multilangtopo.utils;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-15
 * Time: 下午3:03
 */
public class LoadResources {
    private InputStream parameterStream;
    public LoadResources(String resourceName) {
        parameterStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
    }
    public InputStream getParameterInputStream() {
        return parameterStream;
    }
}
