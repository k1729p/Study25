package kp.resource.server;

/**
 * Contains constant values used across the application.
 */
public class Constants {
    /**
     * The classification level secret.
     */
    public static final String LEVEL_SECRET = "LEVEL_SECRET";
    /**
     * The user with the classification level secret.
     */
    public static final String LEVEL_SECRET_USER = "alice";
    /**
     * The classification level confidential
     */
    public static final String LEVEL_CONFIDENTIAL = "LEVEL_CONFIDENTIAL";
    /**
     * The user with the classification level confidential.
     */
    public static final String LEVEL_CONFIDENTIAL_USER = "bob";
    /**
     * The classification level restricted.
     */
    public static final String LEVEL_RESTRICTED = "LEVEL_RESTRICTED";
    /**
     * The user with the classification level restricted.
     */
    public static final String LEVEL_RESTRICTED_USER = "charlie";
    /**
     * The classification level official.
     */
    public static final String LEVEL_OFFICIAL = "LEVEL_OFFICIAL";
    /**
     * The user with the classification level official.
     */
    public static final String LEVEL_OFFICIAL_USER = "david";

    /**
     * Private constructor to prevent instantiation.
     */
    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
