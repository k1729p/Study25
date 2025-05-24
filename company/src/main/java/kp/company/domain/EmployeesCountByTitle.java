package kp.company.domain;

/**
 * Represents the count of employees grouped by their title.
 *
 * @param title the title of the employee
 * @param count the number of employees with the given title
 */
public record EmployeesCountByTitle(Title title, Long count) {
}
