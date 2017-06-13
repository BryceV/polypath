package base.quarter;

import base.Application;
import base.entry.Entry;
import base.flowchart.Flowchart;
import base.flowchart.FlowchartCompact;
import base.flowchart.FlowchartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuarterService {

    @Autowired
    private QuarterRepository quarterRepository;
    @Autowired
    private FlowchartService flowchartService;

    public List<Quarter> getAllQuarters() {
        List<Quarter> quarters = new ArrayList<>();
        quarterRepository.findAll().forEach(quarters::add);
        return quarters;
    }

    public QuarterAnalytics getQuarterAnalytics(Long id)
    {
      Quarter quarter = this.getQuarter(id);
      Analytics analytics = new Analytics(quarter);
      Flowchart flowchart;
      List list = new ArrayList<CourseOccurance>();
      List<FlowchartCompact> offical = flowchartService.getOfficialFlowcharts();
      if(offical == null)
        return new QuarterAnalytics(quarter, Collections.EMPTY_LIST);
      for(FlowchartCompact compact : offical)
      {
        flowchart = flowchartService.getFlowchart(compact.getId());
        for(Entry entry : flowchart.getEntries())
        {
          if(entry.getQuarter().getId().longValue() == id.longValue())
          {
            if(analytics.getMap().get(entry.getCourse().toString()) == null)
            {
              analytics.getMap().put(entry.getCourse().toString(), new CourseOccurance(entry.getCourse().toString(), flowchart.getName()));
            }
            else
            {
              analytics.getMap().replace(entry.getCourse().toString(), analytics.getMap().get(entry.getCourse().toString()).incrementCourseOccurance(flowchart.getName()));
            }
          }
        }
      }
      list = new ArrayList<CourseOccurance>(analytics.getMap().values());
      Collections.sort(list);
      return new QuarterAnalytics(quarter, list);
    }

    public Quarter getQuarter(Long id) {
        return quarterRepository.findOne(id);
    }

    public Quarter getQuarterByTermAndYear(String termYear)
    {
        List<Quarter> quarters = getAllQuarters();
        String temp = "";
        for(Quarter q : quarters)
        {
            temp = "" + q.getTerm() + q.getYear();
            if(temp.equals(termYear))
            {
                return q;
            }
        }
        throw new IllegalArgumentException("No quarter available for : " + termYear);
    }

    public void addQuarter(Quarter quarter)
    {
        quarterRepository.save(quarter);
    }

    public void updateQuarter(Long id, Quarter quarter)
    {
        Quarter temp = quarterRepository.findOne(id);
        if(quarter == null) {
            return;
        }
        else {
            temp.setTerm(quarter.getTerm());
            temp.setYear(quarter.getYear());
            quarterRepository.save(temp);
        }
    }

    public void deleteQuarter(Long id)
    {
        quarterRepository.delete(id);
    }

    public void removeAllQuarters()
    {
        quarterRepository.deleteAll();
    }

    public Quarter getStartOfCurrentYear(){
      return getQuarterByTermAndYear(Application.CURRENT_YEAR_AND_TERM);
    }

    public Quarter nextQuarter(Quarter startQuarter, int numberOfQuartersInTheFuture) {
      int startingYear = startQuarter.getYear();
      Term startingTerm = startQuarter.getTerm();

      int years = numberOfQuartersInTheFuture / 4;
      int additionalQuarters = numberOfQuartersInTheFuture % 4;

      Term endTerm = Term.fromValue((startingTerm.getValue() + additionalQuarters) % 4);
      return getQuarterByTermAndYear(endTerm.name() + (startingYear + years));
    }
}
