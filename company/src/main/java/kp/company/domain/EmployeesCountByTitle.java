package kp.company.domain;

/**
 * The count of the employees by the title.
 *
 * @param title the employee title
 * @param count the employees count
 */
public record EmployeesCountByTitle(Title title, Long count) {
}
