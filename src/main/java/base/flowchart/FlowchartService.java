package base.flowchart;

import base.entry.Entry;
import base.entry.EntryService;
import base.quarter.Quarter;
import base.quarter.QuarterService;
import base.user.User;
import base.user.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class FlowchartService {
  private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private FlowchartRepository flowchartRepository;

    @Autowired
    private QuarterService quarterService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private UserRepository userRepository;

    public List<Flowchart> getAllFlowcharts() {
        List<Flowchart> flowcharts = new ArrayList<>();
        flowchartRepository.findAll().forEach(flowcharts::add);
        return flowcharts;
    }

    public List<Flowchart> getUsersFlowcharts(User user) {
        return user.getFlowcharts();
    }

    public Flowchart getFlowchart(Long id) {
        return flowchartRepository.findOne(id);
    }

    public Flowchart getFlowchartByName(User user, String name) {
        List<Flowchart> temp = user.getFlowcharts();
        for(Flowchart flowchart: temp)
        {
            if(flowchart.getName().equals(name))
            {
                return flowchart;
            }
        }
        return null;
    }

    public void addFlowchart(Flowchart flowchart)
    {
        flowchartRepository.save(flowchart);
    }

    @Transactional
    public Flowchart addFlowchart(User user, String name, String templateFlowchartId)
    {
        Long id = null;
        try {
          id = Long.parseUnsignedLong(templateFlowchartId);
        } catch (NumberFormatException ne) {
          logger.error("Invalid flowchartID: " + templateFlowchartId);
        }

        Flowchart templateFlowchart = null;
        if (id != null) {
          Flowchart temp = flowchartRepository.findOne(id);
          if (temp != null && temp.isOfficial()) {
            templateFlowchart = temp;
          } else {
            logger.error("Invalid flowchartID: " + templateFlowchartId);
          }
        }

        Flowchart newFlowchart = new Flowchart();
        newFlowchart.setName(StringUtils.isEmpty(name) ? "New Flowchart" : name); //TODO We should have a better default
        newFlowchart.setUser(user);

        if (templateFlowchart != null) {
            // copy entries
            List<Entry> entries = new ArrayList<>();
            templateFlowchart.getEntries().forEach(entry -> {
              Entry e = new Entry(entry.getCourse(), newFlowchart, entry.getQuarter(), entry.getColor());
              entryService.addEntry(e);
              entries.add(e);
            });


            newFlowchart.setEntries(entries);
            newFlowchart.setFirstQuarter(templateFlowchart.getFirstQuarter());
            newFlowchart.setLastQuarter(templateFlowchart.getLastQuarter());
        } else {
          // TODO we should allow the user to pass in the starting quarter
          Quarter quarter = quarterService.getStartOfCurrentYear();
          newFlowchart.setFirstQuarter(quarter);
          // Add 4 years by default
          newFlowchart.setLastQuarter(quarterService.nextQuarter(quarter, 4 * 4));
          newFlowchart.setEntries(Collections.emptyList());
        }

        return flowchartRepository.save(newFlowchart);
    }

    // TODO only allow Admins or Owners to make this call

    // Only the flowchart name and entry set can be updated
    public void updateFlowchart(Long id, Map<String,String> flowchartAttributes)
    {
        Flowchart temp = flowchartRepository.findOne(id);
        if(temp == null) {
          logger.debug("Unable to find flowchart in DB: " + id);
          return; // TODO return 404?
        }

        final String name = flowchartAttributes.get("name");
        if (!StringUtils.isEmpty(name)) {
          logger.debug(String.format("Updating flowchart %d name to %s.", id, name));
          temp.setName(name);

          flowchartRepository.save(temp);
        }
    }

    public void deleteFlowchart(Long id)
    {
        flowchartRepository.delete(id);
    }

    public void removeAllFlowcharts()
    {
        flowchartRepository.deleteAll();
    }

    public void publishFlowchart(Long id, boolean official) {
      Flowchart flowchart = flowchartRepository.findOne(id);
      flowchart.setOfficial(official);
      flowchartRepository.save(flowchart);
    }

    public List<FlowchartCompact> getOfficialFlowcharts() {
      List<FlowchartCompact> flowcharts = new ArrayList<>();
      flowchartRepository.findByIsOfficial(true).forEach(f -> {
        flowcharts.add(new FlowchartCompact(f.getId(), f.getName()));
      });
      return flowcharts;
    }
}
