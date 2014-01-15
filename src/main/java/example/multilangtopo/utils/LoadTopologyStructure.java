package example.multilangtopo.utils;



import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
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

    public static void getTopologyComponent() throws IOException {
        LoadResources topologyStructure = new LoadResources("topology.json");
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(topologyStructure.getParameterInputStream());
//        _LOG.info("root NodeType: "+root.getNodeType());
//        Iterator<String> componentTypes = root.fieldNames();
//        while (componentTypes.hasNext()) {
//            String componentType = componentTypes.next();
//            _LOG.info("current Component: "+componentType);
//
//        }
//        JsonNode spoutRoot = root.get("Spout");
//        _LOG.info("spout's NodeType: " + spoutRoot.getNodeType()); //ARRAY
//        Iterator<JsonNode> spoutComponents = spoutRoot.iterator();
//        while (spoutComponents.hasNext()) {
//            JsonNode spoutComponent = spoutComponents.next();
//            _LOG.info("spoutComponentType: " + spoutComponent.getNodeType());
//            Iterator<JsonNode> spoutComponentParas = spoutComponent.
//
//        }
//        JsonParser jsonParser = jsonFactory.createJsonParser(topologyStructure.getParameterInputStream());
        ObjectMapper mapper = new ObjectMapper();
        TopologyComponent topologyComponent = mapper.readValue(topologyStructure.getParameterInputStream(), TopologyComponent.class);
        SpoutComponent[] spoutComponents = topologyComponent.getSpout();
        BoltComponent[] boltComponents = topologyComponent.getBolt();

        for (SpoutComponent spoutComponent : spoutComponents) {
            _LOG.info("ComponentName: "+spoutComponent.getComponentName());
            _LOG.info("ClassName: "+spoutComponent.getClassName());
            _LOG.info("TaskNum: "+spoutComponent.getTaskNum());
        }

        for (BoltComponent boltComponent : boltComponents) {
            _LOG.info("GroupingArgs: "+ Arrays.toString(boltComponent.getGroupingArgs()));
        }

    }

}
