package kp.company.controllers;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

/**
 * The tests for the department controller.
 */
@GraphQlTest(DepartmentController.class)
class DepartmentControllerTests {

    private final GraphQlTester graphQlTester;
    private static final String TEST_DEPARTMENT_JSON = """
                {
                    "id" : "1",
                    "name" : "N-1",
                    "employees" : [ {
                        "id" : "11",
                        "firstName" : "F-N-11",
                        "lastName" : "L-N-11",
                        "title" : "ANALYST"
                    } ]
                }
            """;
    private static final String TEST_DEPARTMENTS_JSON = "[%s]".formatted(TEST_DEPARTMENT_JSON);
    private static final Department TEST_DEPARTMENT = new Department(1, "N-1",
            List.of(new Employee(11, "F-N-11", "L-N-11", Title.ANALYST)));

    /**
     * Parameterized constructor.
     *
     * @param graphQlTester the {@link GraphQlTester}
     */
    DepartmentControllerTests(@Autowired GraphQlTester graphQlTester) {
        this.graphQlTester = graphQlTester;
    }

    /**
     * Executed before each test.
     */
    @BeforeEach
    void setup() {
        graphQlTester.documentName("mutateDepartments").execute();
    }

    /**
     * Should mutate departments.
     */
    @Test
    @DisplayName("🟩 should mutate departments")
    void shouldMutateDepartments() {
        // GIVEN
        final GraphQlTester.Request<?> request = graphQlTester.documentName("mutateDepartments");
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.path("mutateDepartments").matchesJson("""
                    {
                        "success" : true
                    }
                """);
    }

    /**
     * Should query all departments.
     */
    @Test
    @DisplayName("🟩 should query all departments")
    void shouldQueryAllDepartments() {
        // GIVEN
        final GraphQlTester.Request<?> request = graphQlTester.documentName("queryDepartments");
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.path("queryDepartments").matchesJson(TEST_DEPARTMENTS_JSON);
        response.path("queryDepartments").entityList(Department.class).hasSize(1);
        response.path("queryDepartments").entityList(Department.class).contains(TEST_DEPARTMENT);
    }

    /**
     * Should query department by id.
     */
    @Test
    @DisplayName("🟩 should query department by id")
    void shouldQueryDepartmentById() {
        // GIVEN
        final GraphQlTester.Request<?> request = graphQlTester
                .documentName("queryDepartmentById").variable("id", "1");
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.path("queryDepartmentById").matchesJson(TEST_DEPARTMENT_JSON);
        response.path("queryDepartmentById").entity(Department.class).isEqualTo(TEST_DEPARTMENT);
    }

    /**
     * Should not query department by id with unknown id.
     */
    @Test
    @DisplayName("🟥 should not query department by id with unknown id")
    void shouldNotQueryDepartmentByIdWithUnknownId() {
        // GIVEN
        final GraphQlTester.Request<?> request = graphQlTester
                .documentName("queryDepartmentById").variable("id", "99999");
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.path("queryDepartmentById").matchesJson("""
                    {
                        "id" : "0",
                        "name" : "",
                        "employees" : []
                    }
                """);
    }

    /**
     * Should query employees count by title.
     */
    @Test
    @DisplayName("🟩 should query employees count by title")
    void shouldQueryEmployeesCountByTitle() {
        // GIVEN
        final GraphQlTester.Request<?> request = graphQlTester
                .documentName("queryEmployeesCountByTitle");
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.path("queryEmployeesCountByTitle").matchesJson("""
                    [ {
                        "title" : "ANALYST",
                        "count" : 1
                    } ]
                """);
    }

}
