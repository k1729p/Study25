package kp.company.controllers;

import kp.company.domain.Department;
import kp.company.domain.DepartmentMutationResult;
import kp.company.domain.Employee;
import kp.company.domain.EmployeesCountByTitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The controller for the department.
 */
@Controller
public class DepartmentController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    private static List<Department> departmentList = new ArrayList<>();

    /**
     * Replaces the departments.
     *
     * @param departmentList the list of the departments
     * @return the list of the departments
     */
    @MutationMapping(name = "mutateDepartments")
    public DepartmentMutationResult mutateDepartments(@Argument(name = "departments") List<Department> departmentList) {

        replace(departmentList);
        logger.info("mutateDepartments(): departments size[{}]", departmentList.size());
        return new DepartmentMutationResult(true);
    }

    /**
     * Gets the list of the departments.
     *
     * @return the list of the departments
     */
    @QueryMapping(name = "queryDepartments")
    public List<Department> queryDepartments() {

        logger.info("queryDepartments(): departments size[{}]", departmentList.size());
        return departmentList;
    }

    /**
     * Gets the department by the id.
     *
     * @param id the department id
     * @return the department
     */
    @QueryMapping(name = "queryDepartmentById")
    public Department queryDepartmentById(@Argument String id) {

        final Optional<Department> departmentOpt = departmentList.stream()
                .filter(dep -> String.valueOf(dep.id()).equals(id)).findFirst();
        if (departmentOpt.isEmpty()) {
            logger.info("queryDepartmentById(): department with id[{}] not found", id);
            return new Department(0, "", new ArrayList<>());
        }
        final Department department = departmentOpt.get();
        logger.info("queryDepartmentById(): id[{}], name[{}]", id, department.name());
        return department;
    }

    /**
     * Gets the count of the employees by the title.
     *
     * @return the list with the counts
     */
    @QueryMapping(name = "queryEmployeesCountByTitle")
    public List<EmployeesCountByTitle> queryEmployeesCountByTitle() {

        final List<EmployeesCountByTitle> employeesCountList = departmentList.stream()
                .map(Department::employees).flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Employee::title, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new EmployeesCountByTitle(entry.getKey(), entry.getValue())).toList();
        logger.info("queryEmployeesCountByTitle(): employeesCountList size[{}]", employeesCountList.size());
        return employeesCountList;
    }

    /**
     * Replaces the department list.
     *
     * @param departmentListParam the department list.
     */
    private static void replace(List<Department> departmentListParam) {
        departmentList = departmentListParam;
    }
}