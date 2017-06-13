package base.entry;

import base.course.Course;
import base.flowchart.Flowchart;
import base.quarter.Quarter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, resolver = SimpleObjectIdResolver.class,
//        property = "id", scope=Entry.class)
@JsonDeserialize(using = EntryDeserializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnoreProperties("entries")
    @ManyToOne
    @JoinColumn(name = "course_id")
//    @JsonIdentityReference(alwaysAsId = true)
    private Course course;

    @JsonIgnoreProperties("entries")
    @ManyToOne
    @JoinColumn(name = "flowchart_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Flowchart flowchart;

    @JsonIgnoreProperties("entries")
    @ManyToOne
    @JoinColumn(name = "quarter_id")
//    @JsonIdentityReference(alwaysAsId = true)
    private Quarter quarter;

    private String color;

    public Entry(){

    }

    public Entry(Course course, Flowchart flowchart, Quarter quarter) {
        this.course = course;
        this.flowchart = flowchart;
        this.quarter = quarter;
    }

  public Entry(Course course, Flowchart flowchart, Quarter quarter, String color) {
    this.course = course;
    this.flowchart = flowchart;
    this.quarter = quarter;
    this.color = color;
  }

    public Entry(Entry entry) {
        this.id = entry.id;
        this.course = entry.course;
        this.flowchart = entry.flowchart;
        this.quarter = entry.quarter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Flowchart getFlowchart() {
        return flowchart;
    }

    public void setFlowchart(Flowchart flowchart) {
        this.flowchart = flowchart;
    }

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
        this.quarter = quarter;
    }

  public String getColor() {
    return color;
  }
}
