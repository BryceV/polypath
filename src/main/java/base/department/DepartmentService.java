package base.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        departmentRepository.findAll().forEach(departments::add);
        return departments;
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findOne(id);
    }

    public Department getDepartmentByName(String prefix) {
        return departmentRepository.findDeparmentByPrefixIgnoreCase(prefix);
    }

    public void addDepartment(Department department)
    {
        departmentRepository.save(department);
    }

    public void updateDepartment(Long id, Department department)
    {
        Department temp = departmentRepository.findOne(id);
        if(department == null) {
            return;
        }
        else {
            temp.setId(department.getId());
            temp.setPrefix(department.getPrefix());
            departmentRepository.save(temp);
        }
    }

    public void updateDepartment(Department department)
    {
        departmentRepository.save(department);
    }

    public void deleteDepartment(Long id)
    {
        departmentRepository.delete(id);
    }

    public void removeAllDepartments()
    {
        departmentRepository.deleteAll();
    }
}
