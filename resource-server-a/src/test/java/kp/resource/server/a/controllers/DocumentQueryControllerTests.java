package kp.resource.server.a.controllers;

import kp.resource.server.services.DocumentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.WebGraphQlTester;

import java.lang.invoke.MethodHandles;
import java.util.Map;

import static kp.resource.server.a.controllers.TestConstants.*;

/**
 * The tests for the {@link DocumentQueryController}
 */
@SpringBootTest
@AutoConfigureHttpGraphQlTester
class DocumentQueryControllerTests {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final Map<String, WebGraphQlTester> testersMap;

    /**
     * Parameterized constructor.
     *
     * @param webGraphQlTester the {@link WebGraphQlTester}
     */
    DocumentQueryControllerTests(@Autowired WebGraphQlTester webGraphQlTester) {
        testersMap = TestHelper.createTestersMap(webGraphQlTester);
        DocumentService.addDocumentForTesting(TEST_DOCUMENT.id(), TEST_DOCUMENT);
    }

    /**
     * Should query document with correct classification level.
     *
     * @param classificationLevel the classification level
     */
    @ParameterizedTest
    @CsvSource({"Official", "Restricted", "Confidential", "Secret"})
    @DisplayName("🟩 should query document with correct classification level")
    void shouldQueryDocument(String classificationLevel) {
        // GIVEN
        final String documentName = "queryDocumentClassificationLevel" + classificationLevel;
        final GraphQlTester.Request<?> request = testersMap.get("With Classification Level " + classificationLevel)
                .documentName(documentName);
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.path("queryDocument[0].officialInformation")
                .entity(String.class).isEqualTo(TEST_DOCUMENT.officialInformation());
        if (!"Official".equals(classificationLevel)) {
            response.path("queryDocument[0].restrictedInformation")
                    .entity(String.class).isEqualTo(TEST_DOCUMENT.restrictedInformation());
            if (!"Restricted".equals(classificationLevel)) {
                response.path("queryDocument[0].confidentialInformation")
                        .entity(String.class).isEqualTo(TEST_DOCUMENT.confidentialInformation());
                if (!"Confidential".equals(classificationLevel)) {
                    response.path("queryDocument[0].secretInformation")
                            .entity(String.class).isEqualTo(TEST_DOCUMENT.secretInformation());
                }
            }
        }
        logger.info("shouldQueryDocument(): classificationLevel[{}]", classificationLevel);
    }

    /**
     * Should not query document without the correct classification level.
     *
     * @param classificationLevel   the classification level
     */
    @ParameterizedTest
    @CsvSource({"Official", "Restricted", "Confidential"})
    @DisplayName("🟥 should not query document without correct classification level")
    void shouldNotQueryDocumentWithoutCorrectClassificationLevel(String classificationLevel) {
        // GIVEN
        final GraphQlTester.Request<?> request = testersMap.get("With Classification Level " + classificationLevel)
                .documentName("queryDocumentClassificationLevelSecret");
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.errors().satisfy(errors -> {
            errors.forEach(error ->
                    Assertions.assertThat(error.getErrorType()).isEqualTo(ErrorType.FORBIDDEN));
            switch (classificationLevel) {
                case "Official" -> {
                    Assertions.assertThat(errors).hasSize(3);
                    Assertions.assertThat(errors.getFirst().getPath())
                            .isEqualTo("queryDocument[0].restrictedInformation");
                    Assertions.assertThat(errors.get(1).getPath())
                            .isEqualTo("queryDocument[0].confidentialInformation");
                }
                case "Restricted" -> {
                    Assertions.assertThat(errors).hasSize(2);
                    Assertions.assertThat(errors.getFirst().getPath())
                            .isEqualTo("queryDocument[0].confidentialInformation");
                }
                case "Confidential" -> Assertions.assertThat(errors).hasSize(1);
                case null, default -> Assertions.fail(
                        "Unexpected classification level: " + classificationLevel);
            }
            Assertions.assertThat(errors.getLast().getPath())
                    .isEqualTo("queryDocument[0].secretInformation");
        });
        logger.info("shouldNotQueryDocumentWithoutCorrectClassificationLevel(): classificationLevel[{}]",
                classificationLevel);
    }

    /**
     * Should not query document when unauthorized.
     *
     * @param classificationLevel the classification level
     */
    @ParameterizedTest
    @CsvSource({"Official", "Restricted", "Confidential", "Secret"})
    @DisplayName("🟥 should not query document when unauthorized")
    void shouldNotQueryDocumentWhenUnauthorized(String classificationLevel) {
        // GIVEN
        final String documentName = "queryDocumentClassificationLevel" + classificationLevel;
        final GraphQlTester.Request<?> request = testersMap.get("Without Authorization").documentName(documentName);
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.errors().satisfy(UNAUTHORIZED_ERROR_CHECKER);
        logger.info("shouldNotQueryDocumentWhenUnauthorized(): classificationLevel[{}]", classificationLevel);
    }

    /**
     * Should not query document with invalid credentials.
     *
     * @param classificationLevel the classification level
     */
    @ParameterizedTest
    @CsvSource({"Official", "Restricted", "Confidential", "Secret"})
    @DisplayName("🟥 should not query document with invalid credentials")
    void shouldNotQueryDocumentWithInvalidCredentials(String classificationLevel) {
        // GIVEN
        final String documentName = "queryDocumentClassificationLevel" + classificationLevel;
        final GraphQlTester.Request<?> request = testersMap.get("With Invalid Credentials").documentName(documentName);
        // WHEN, THEN
        Assertions.assertThatThrownBy(request::executeAndVerify).hasMessage(EXCEPTION_MSG_UNAUTHORIZED);
        logger.info("shouldNotQueryDocumentWithInvalidCredentials(): classificationLevel[{}]", classificationLevel);
    }

}
