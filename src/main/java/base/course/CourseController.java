package base.course;

import base.department.DepartmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class CourseController {

  @Autowired
  private CourseService courseService;
  @Autowired
  private DepartmentService departmentService;

  /**
   * Gets courses from the catalog. If a search term is provided, the list is filtered
   * based on that search term. Otherwise all Courses are returned.
   * @param searchTerm the search term to search on
   * @return the list of Courses
   */
  @RequestMapping("courses")
  public List<Course> getAllCourses(@RequestParam(value = "searchTerm", required = false) String searchTerm) {
    if (!StringUtils.isEmpty(searchTerm)) {
      return courseService.getCoursesBySearchTerm(searchTerm);
    } else {
      return courseService.getAllCourses();
    }
  }

  @RequestMapping("courses/{id}")
  public Course getCourse(@PathVariable Long id) {
    return courseService.getCourse(id);
  }

  @RequestMapping(method = RequestMethod.POST, value = "courses")
  public void addCourse(@RequestBody Course course) {
    courseService.addCourse(course);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "courses/{id}")
  public void updateCourse(@PathVariable Long id, @RequestBody Course course) {
    courseService.updateCourse(id, course);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "courses/{id}")
  public void deleteCourse(@PathVariable Long id) {
    courseService.deleteCourse(id);
  }

}
