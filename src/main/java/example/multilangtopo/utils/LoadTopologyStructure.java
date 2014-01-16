package example.multilangtopo.utils;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created with IntelliJ IDEA.
 * User: Lin QiLi
 * Date: 14-1-15
 * Time: 下午3:05
 */
public class LoadTopologyStructure {

    private static final Logger _LOG = Logger.getLogger(LoadTopologyStructure.class);

    public static List<BaseComponent[]> getTopologyComponent() throws IOException {
        LoadResources topologyStructure = new LoadResources("topology.json");

        ObjectMapper mapper = new ObjectMapper();
        TopologyComponent topologyComponent = mapper.readValue(topologyStructure.getParameterInputStream(), TopologyComponent.class);
        SpoutComponent[] spoutComponents = topologyComponent.getSpout();
        BoltComponent[] boltComponents = topologyComponent.getBolt();

        for (SpoutComponent spoutComponent : spoutComponents) {
            _LOG.info("ComponentName: "+spoutComponent.getComponent_name());
            _LOG.info("ClassName: "+spoutComponent.getClass_name());
            _LOG.info("TaskNum: "+spoutComponent.getTask_num());
        }

        for (BoltComponent boltComponent : boltComponents) {
            _LOG.info("GroupingArgs: "+ Arrays.toString(boltComponent.getGrouping_args()));
        }

        List<BaseComponent[]> returnList = new ArrayList<BaseComponent[]>();
        returnList.add(0, spoutComponents);
        returnList.add(1, boltComponents);

        return  returnList;
    }

}
