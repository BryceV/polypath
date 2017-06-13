package base.data;

import base.course.Course;
import base.course.CourseService;
import base.department.Department;
import base.department.DepartmentService;
import base.entry.EntryService;
import base.flowchart.FlowchartService;
import base.quarter.Quarter;
import base.quarter.QuarterService;
import base.quarter.Term;
import base.security.user.RoleType;
import base.user.User;
import base.user.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private CourseService courseService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EntryService entryService;
    @Autowired
    private FlowchartService flowchartService;
    @Autowired
    private QuarterService quarterService;
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

      if(userService.getUser("jpautz@calpoly.edu") == null)
      {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CatalogCourse> catalogCourseList = objectMapper.readValue(ResourceUtils.getURL("src/main/resources/data/courses.json").openStream(), new TypeReference<List<CatalogCourse>>(){});

        for (CatalogCourse catalogCourse : catalogCourseList){
          //check if department exists, if not create it
          Department dept = departmentService.getDepartmentByName(catalogCourse.getPrefix());
          if (dept == null) {
            dept = new Department(catalogCourse.getPrefix());
            departmentService.addDepartment(dept);
          }
          //add course to list
          courseService.addCourse(new Course(catalogCourse.getSuffix(), catalogCourse.getTitle(), catalogCourse.getDescription(), catalogCourse.getUnits(), dept));
        }

        //Add GEs
        Department department = new Department("GE");
        departmentService.addDepartment(department);
        courseService.addCourse(new Course("A1", "General Education A1", "", "1-4", department));
        courseService.addCourse(new Course("A2", "General Education A2", "", "1-4", department));
        courseService.addCourse(new Course("A3", "General Education A3", "", "1-4", department));

        courseService.addCourse(new Course("B1", "General Education B1", "", "1-4", department));
        courseService.addCourse(new Course("B2", "General Education B2", "", "1-4", department));
        courseService.addCourse(new Course("B3", "General Education B3", "", "1-4", department));
        courseService.addCourse(new Course("B4", "General Education B4", "", "1-4", department));
        courseService.addCourse(new Course("B5", "General Education B5", "", "1-4", department));
        courseService.addCourse(new Course("B6", "General Education B6", "", "1-4", department));

        courseService.addCourse(new Course("C1", "General Education C1", "", "1-4", department));
        courseService.addCourse(new Course("C2", "General Education C2", "", "1-4", department));
        courseService.addCourse(new Course("C3", "General Education C3", "", "1-4", department));
        courseService.addCourse(new Course("C4", "General Education C4", "", "1-4", department));
        courseService.addCourse(new Course("C5", "General Education C5", "", "1-4", department));

        courseService.addCourse(new Course("D1", "General Education D1", "", "1-4", department));
        courseService.addCourse(new Course("D2", "General Education D2", "", "1-4", department));
        courseService.addCourse(new Course("D3", "General Education D3", "", "1-4", department));
        courseService.addCourse(new Course("D4", "General Education D4", "", "1-4", department));
        courseService.addCourse(new Course("D5", "General Education D5", "", "1-4", department));

        //Add Tech Elective
        department = new Department("TECH");
        departmentService.addDepartment(department);
        courseService.addCourse(new Course("", "Technical Elective", "", "1-4", department));


        // Add Quarters
        ArrayList<Quarter> testQuarters = new ArrayList<>();
        testQuarters.add(new Quarter(Term.FALL, 2016));
        testQuarters.add(new Quarter(Term.WINTER, 2017));
        testQuarters.add(new Quarter(Term.SPRING, 2017));
//        testQuarters.add(new Quarter(Term.SUMMER, 2017));
        testQuarters.add(new Quarter(Term.FALL, 2017));
        testQuarters.add(new Quarter(Term.WINTER, 2018));
        testQuarters.add(new Quarter(Term.SPRING, 2018));
//        testQuarters.add(new Quarter(Term.SUMMER, 2018));
        testQuarters.add(new Quarter(Term.FALL, 2018));
        testQuarters.add(new Quarter(Term.WINTER, 2019));
        testQuarters.add(new Quarter(Term.SPRING, 2019));
//        testQuarters.add(new Quarter(Term.SUMMER, 2019));
        testQuarters.add(new Quarter(Term.FALL, 2019));
        testQuarters.add(new Quarter(Term.WINTER, 2020));
        testQuarters.add(new Quarter(Term.SPRING, 2020));
//        testQuarters.add(new Quarter(Term.SUMMER, 2020));
        testQuarters.add(new Quarter(Term.FALL, 2020));
        testQuarters.add(new Quarter(Term.WINTER, 2021));
        testQuarters.add(new Quarter(Term.SPRING, 2021));
//        testQuarters.add(new Quarter(Term.SUMMER, 2021));
        testQuarters.add(new Quarter(Term.FALL, 2021));
        testQuarters.add(new Quarter(Term.WINTER, 2022));
        testQuarters.add(new Quarter(Term.SPRING, 2022));
//        testQuarters.add(new Quarter(Term.SUMMER, 2022));
        testQuarters.add(new Quarter(Term.FALL, 2022));
        testQuarters.add(new Quarter(Term.WINTER, 2023));
        testQuarters.add(new Quarter(Term.SPRING, 2023));
//        testQuarters.add(new Quarter(Term.SUMMER, 2023));
        testQuarters.add(new Quarter(Term.FALL, 2023));
        testQuarters.add(new Quarter(Term.WINTER, 2024));
        testQuarters.add(new Quarter(Term.SPRING, 2024));
//        testQuarters.add(new Quarter(Term.SUMMER, 2024));
        testQuarters.forEach(quarter -> quarterService.addQuarter(quarter));

        Quarter startQuarter = quarterService.getStartOfCurrentYear();
        Quarter endQuarter = quarterService.nextQuarter(startQuarter, 4 * 4);

        // Add users
        ArrayList<User> testUsers = new ArrayList<>();
        testUsers.add(new User("Gudrun", "Socher", "gsocher@calpoly.edu", "password"));
        testUsers.add(new User("Michael", "Crivello", "macrivel@calpoly.edu", "password1"));
        testUsers.add(new User("Jonathan", "Pautz", "jpautz@calpoly.edu", "password2"));
        testUsers.add(new User("Matt", "Jimenez", "mpjimene@calpoly.edu", "password3"));
        testUsers.add(new User("Bryce", "Vonilten", "bvonilte@calpoly.edu", "password4"));
        testUsers.add(new User("Miguel", "Duran", "mduran@calpoly.edu", "password5"));

        Set<RoleType> staffRoles = new HashSet<>();
        staffRoles.add(RoleType.CATALOG_ADMIN);
        testUsers.add(new User("Registrar", "Staff", "staff@calpoly.edu", "staff", staffRoles));

        testUsers.forEach(u -> userService.createNewUser(u));
      }
    }
}
