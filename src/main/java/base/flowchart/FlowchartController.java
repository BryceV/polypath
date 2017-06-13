package base.flowchart;

import base.security.user.CurrentUser;
import base.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class FlowchartController {

    @Autowired
    private FlowchartService flowchartService;

    @RequestMapping("flowcharts")
    public List<Flowchart> getAllFlowcharts(@CurrentUser User user) {
        return flowchartService.getUsersFlowcharts(user);
    }

    @RequestMapping("flowcharts/all")
    public List<Flowchart> getAllFlowcharts(){
        return flowchartService.getAllFlowcharts();
    }

    @RequestMapping("flowcharts/{id}")
    public Flowchart getFlowchart(@PathVariable Long id) {
        return flowchartService.getFlowchart(id);
    }

    @RequestMapping(method= RequestMethod.POST, value="flowcharts")
    public Flowchart addFlowchart(@CurrentUser User user, @RequestBody Map<String, String> attributes) {
        String name = attributes.get("name");
        String flowchartId = attributes.get("templateId");
        return flowchartService.addFlowchart(user, name, flowchartId);
    }

    @RequestMapping(method=RequestMethod.PUT, value="flowcharts/{id}")
    public void updateFlowchart(@PathVariable Long id, @RequestBody Map<String, String> flowchartAttributes) {
        flowchartService.updateFlowchart(id, flowchartAttributes);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="flowcharts/{id}")
    public void deleteFlowchart(@PathVariable Long id) {
        flowchartService.deleteFlowchart(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "flowcharts/{id}/publish")
    public void publishFlowchart(@PathVariable Long id, @RequestBody String markOfficial) {
      flowchartService.publishFlowchart(id, Boolean.parseBoolean(markOfficial));
    }

    @RequestMapping("flowcharts/official")
    public List<FlowchartCompact> getOfficialFlowcharts(){
      return flowchartService.getOfficialFlowcharts();
    }
}
