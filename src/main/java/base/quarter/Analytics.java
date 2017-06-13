package base.quarter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analytics {

  Map<String, CourseOccurance> map;

  public Analytics(Quarter quarter)
  {
    map = new HashMap<>();
  }

  public Map<String, CourseOccurance> getMap() {
    return map;
  }
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class QuarterAnalytics {
  Quarter quarter;
  List<CourseOccurance>  occurances;

  public QuarterAnalytics(Quarter quarter, List<CourseOccurance> occurances) {
    this.quarter = quarter;
    this.occurances = occurances;
  }
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class CourseOccurance implements Comparable {
  String courseName;
  int occurences;
  List<String> flowchartNames;

  public CourseOccurance(String courseName, String flowchartName) {
    this.occurences = 1;
    this.flowchartNames = new ArrayList<>();
    this.flowchartNames.add(flowchartName);
    this.courseName = new String(courseName);
  }

  //Higher numbers first
  public int compareTo(Object o)
  {
    CourseOccurance courseOccurance = (CourseOccurance) o;
    return courseOccurance.getOccurences() - this.occurences;
  }

  private CourseOccurance()
  {

  }

  public CourseOccurance incrementCourseOccurance(String flowchartName) {
    CourseOccurance temp = new CourseOccurance();
    temp.setOccurences(occurences+1);
    this.flowchartNames.add(flowchartName);
    temp.setFlowchartNames(this.flowchartNames);
    temp.courseName = new String(this.courseName);
    return temp;
  }

  public int getOccurences() {
    return occurences;
  }

  private  void setOccurences(int occurences){
    this.occurences = occurences;
  }

  public List<String> getFlowchartNames() {
    return flowchartNames;
  }

  private  void setFlowchartNames(List<String> names){
    this.flowchartNames = names;
  }
}


