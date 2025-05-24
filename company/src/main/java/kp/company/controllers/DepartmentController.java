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
import java.util.stream.Collectors;

/**
 * Controller for department-related GraphQL queries and mutations.
 */
@Controller
public class DepartmentController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    private static List<Department> departmentList = new ArrayList<>();

    /**
     * Replaces the list of departments.
     *
     * @param departmentList the list of departments to set
     * @return the mutation result
     */
    @MutationMapping(name = "mutateDepartments")
    public DepartmentMutationResult mutateDepartments(@Argument(name = "departments") List<Department> departmentList) {

        replace(departmentList);
        logger.info("mutateDepartments(): departments size [{}]", departmentList.size());
        return new DepartmentMutationResult(true);
    }

    /**
     * Gets the list of departments.
     *
     * @return the list of departments
     */
    @QueryMapping(name = "queryDepartments")
    public List<Department> queryDepartments() {

        logger.info("queryDepartments(): departments size [{}]", departmentList.size());
        return departmentList;
    }

    /**
     * Gets a department by its id.
     *
     * @param id the department id
     * @return the department, or a default department if not found
     */
    @QueryMapping(name = "queryDepartmentById")
    public Department queryDepartmentById(@Argument String id) {

        final Department department = departmentList.stream()
                .filter(dep -> String.valueOf(dep.id()).equals(id)).findFirst()
                .orElseGet(() -> {
                    logger.info("queryDepartmentById(): department not found, id[{}]", id);
                    return new Department(0, "", new ArrayList<>());
                });
        logger.info("queryDepartmentById(): id[{}], name[{}]", id, department.name());
        return department;
    }

    /**
     * Gets the count of employees by title across all departments.
     *
     * @return the list of employee counts by title
     */
    @QueryMapping(name = "queryEmployeesCountByTitle")
    public List<EmployeesCountByTitle> queryEmployeesCountByTitle() {

        final List<EmployeesCountByTitle> employeesCountList = departmentList.stream()
                .map(Department::employees).flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Employee::title, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new EmployeesCountByTitle(entry.getKey(), entry.getValue()))
                .toList();
        logger.info("queryEmployeesCountByTitle(): employeesCountList size[{}]", employeesCountList.size());
        return employeesCountList;
    }

    /**
     * Replaces the department list.
     *
     * @param departmentListParam the new department list
     */
    private static void replace(List<Department> departmentListParam) {
        departmentList = departmentListParam;
    }
}