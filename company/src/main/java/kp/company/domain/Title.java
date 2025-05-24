package kp.company.domain;

/**
 * The job title of the employee.
 */
@SuppressWarnings({"UnusedDeclaration"})
public enum Title {
    /**
     * The analyst
     */
    ANALYST("Analyst"),
    /**
     * The developer
     */
    DEVELOPER("Developer"),
    /**
     * The manager
     */
    MANAGER("Manager");

    private final String name;

    /**
     * Parameterized constructor.
     *
     * @param name the name
     */
    Title(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }
}