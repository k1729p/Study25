package kp.resource.server.b.controllers;

import kp.resource.server.domain.Document;
import org.springframework.graphql.ResponseError;
import org.springframework.graphql.execution.ErrorType;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Contains constant values used across the tests.
 */
class TestConstants {
    /**
     * The secret password.
     */
    public static final String PASSWORD_SECRET = "clandestine";
    /**
     * The confidential password.
     */
    public static final String PASSWORD_CONFIDENTIAL = "clandestine";
    /**
     * The restricted password.
     */
    public static final String PASSWORD_RESTRICTED = "clandestine";
    /**
     * The official password.
     */
    public static final String PASSWORD_OFFICIAL = "clandestine";
    /**
     * The test document.
     */
    public static final Document TEST_DOCUMENT = new Document("1",
            "official test info", "restricted test info",
            "confidential test info", "secret test info");
    /**
     * The unauthorized exception message.
     */
    static final String EXCEPTION_MSG_UNAUTHORIZED = "Status expected:<200 OK> but was:<401 UNAUTHORIZED>";
    /**
     * The unauthorized error checker.
     */
    static final Consumer<List<ResponseError>> UNAUTHORIZED_ERROR_CHECKER = errors -> {
        assertThat(errors).hasSize(1);
        assertThat(errors.getFirst().getErrorType()).isEqualTo(ErrorType.UNAUTHORIZED);
    };
    /**
     * The forbidden error checker.
     */
    static final Consumer<List<ResponseError>> FORBIDDEN_ERROR_CHECKER = errors -> {
        assertThat(errors).hasSize(1);
        assertThat(errors.getFirst().getErrorType()).isEqualTo(ErrorType.FORBIDDEN);
    };

    /**
     * Private constructor to prevent instantiation.
     */
    private TestConstants() {
        throw new IllegalStateException("Utility class");
    }
}
