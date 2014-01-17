package example.multilangtopo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-17
 * Time: 下午2:14
 */
public class LoadTopologyConf {
    private static Logger _LOG = Logger.getLogger(LoadTopologyConf.class);

    public static TopologyConf getTopoConf() throws IOException {
        LoadResources topologyConfResources = new LoadResources("conf.json");

        ObjectMapper mapper = new ObjectMapper();

        TopologyConf topologyConf = mapper.readValue(topologyConfResources.getParameterInputStream(), TopologyConf.class);
        _LOG.info("localclustersleepmsecs: "+topologyConf.getLocalclustersleepmsecs());
        _LOG.info("maxtaskparallelism: "+topologyConf.getMaxtaskparallelism());
        _LOG.info("workersnum: "+topologyConf.getWorkersnum());
        _LOG.info("ackersnum: "+topologyConf.getAckersnum());
        _LOG.info("maxspoutpending: "+topologyConf.getMaxspoutpending());
        _LOG.info("debug: "+topologyConf.isDebug());
        _LOG.info("messagetimeoutsecs: "+topologyConf.getMessagetimeoutsecs());

        return topologyConf;

    }
}
