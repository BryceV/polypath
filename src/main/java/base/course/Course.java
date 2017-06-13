package base.course;

import base.department.Department;
import base.entry.Entry;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, resolver = SimpleObjectIdResolver.class, property = "id", scope=Course.class)
public class Course implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String number;
  private String title;
  private String units;

  @Column(length = 1024)
  private String description;

  @JsonIgnore // dont need to serialize entries
  @JsonIgnoreProperties("course")
  @OneToMany(targetEntity = Entry.class, mappedBy = "course",
    cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Entry> entries;

  @JsonIgnoreProperties("courses")
  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

  public Course() {
  }

  public Course(String number, String title, String description, String units, Department department) {
    this.number = number;
    this.title = title;
    this.description = description;
    this.department = department;
    this.units = units;
  }

  public Course(Course course) {
    this.id = course.id;
    this.number = course.number;
    this.title = course.title;
    this.units = course.units;
    this.description = course.description;
    this.entries = course.entries;
    this.department = course.department;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUnits()
  {
    return units;
  }

  public void setUnits(String units)
  {
    this.units = units;
  }

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String toString() {
	  return getDepartment().getPrefix() + " " + getNumber() + " " + getTitle();
	}
}


