package base.course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;

  /**
   * Gets all courses in the catalog.
   * @return the list of Courses
   */
  public List<Course> getAllCourses() {
    List<Course> courses = new ArrayList<>();
    courseRepository.findAll().forEach(courses::add);
    return courses;
  }

  /**
   * Searches for Coureses that match a given search term.
   * The search term can match across the department,
   * course number, and course name.
   * @param searchTerm the search term
   * @return the list of Courses that match
   */
  public List<Course> getCoursesBySearchTerm(String searchTerm) {
    return StreamSupport.stream(courseRepository.findAll().spliterator(), true)
      .filter(c -> c.toString().toLowerCase().contains(searchTerm.toLowerCase()))
      .collect(Collectors.toList());
  }

  public Course getCourse(Long id) {
    return courseRepository.findOne(id);
  }

  public Course getCourseByTitle(String title) {
    return courseRepository.findCourseByTitle(title);
  }

  public void addCourse(Course course) {
    courseRepository.save(course);
  }

  /**
   * Updates a Course to match a given Course.
   * @param id the id of the course to update
   * @param course the Course information used to update
   */
  public void updateCourse(Long id, Course course) {
    Course temp = courseRepository.findOne(id);
    if (course != null) {
      temp.setId(course.getId());
      temp.setNumber(course.getNumber());
      temp.setTitle(course.getTitle());
      temp.setUnits(course.getUnits());
      temp.getDepartment().setId(course.getDepartment().getId());
      temp.getDepartment().setPrefix(course.getDepartment().getPrefix());
      courseRepository.save(temp);
    }
  }

  public void deleteCourse(Long id) {
    courseRepository.delete(id);
  }

  public void removeAllCourses() {
    courseRepository.deleteAll();
  }

  public List<Course> getAllCoursesByDepartment(String dept) {
    return courseRepository.findByDepartment(dept.toUpperCase());
  }
}
