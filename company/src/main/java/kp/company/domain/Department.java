package kp.company.domain;

import java.util.List;

/**
 * The department.
 *
 * @param id        the id
 * @param name      the name
 * @param employees the list of the {@link Employee}s
 */
public record Department(int id, String name, List<Employee> employees) {
}