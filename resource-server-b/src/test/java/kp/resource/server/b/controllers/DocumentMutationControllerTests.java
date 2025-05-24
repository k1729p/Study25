package kp.resource.server.b.controllers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.WebGraphQlTester;

import java.lang.invoke.MethodHandles;
import java.util.Map;

import static kp.resource.server.b.controllers.TestConstants.*;

/**
 * Tests for the {@link DocumentMutationController}.
 */
@SpringBootTest
@AutoConfigureHttpGraphQlTester
class DocumentMutationControllerTests {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final Map<String, WebGraphQlTester> testersMap;

    /**
     * Parameterized constructor.
     *
     * @param webGraphQlTester the {@link WebGraphQlTester} instance
     */
    DocumentMutationControllerTests(@Autowired WebGraphQlTester webGraphQlTester) {
        testersMap = TestHelper.createTestersMap(webGraphQlTester);
    }

    /**
     * Verifies that a document is mutated with the correct classification level.
     *
     * @param classificationLevel the classification level
     */
    @ParameterizedTest
    @CsvSource({"Official", "Restricted", "Confidential", "Secret"})
    @DisplayName("🟩 should mutate document with correct classification level")
    void shouldMutateDocument(String classificationLevel) {
        // GIVEN
        final String documentName = "mutateDocumentClassificationLevel" + classificationLevel;
        final GraphQlTester.Request<?> request = testersMap.get("With Classification Level " + classificationLevel)
                .documentName(documentName).variable("documentInput", TEST_DOCUMENT);
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.path(documentName + ".success").entity(Boolean.class).isEqualTo(true);
        response.path(documentName + ".id").entity(String.class).isEqualTo("1");
        logger.info("shouldMutateDocument(): classificationLevel[{}]", classificationLevel);
    }

    /**
     * Verifies that all documents are deleted.
     */
    @Test
    @DisplayName("🟩 should delete all documents")
    void shouldDeleteAllDocuments() {
        // GIVEN
        final String documentName = "deleteAllDocuments";
        final GraphQlTester.Request<?> request = testersMap.get("With Classification Level Secret")
                .documentName(documentName);
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.path(documentName + ".success").entity(Boolean.class).isEqualTo(true);
        logger.info("shouldDeleteAllDocuments():");
    }

    /**
     * Ensures that mutation does not occur without the required classification level.
     *
     * @param expectedClassificationLevel the required classification level
     * @param actualClassificationLevel   the provided classification level
     */
    @ParameterizedTest
    @CsvSource({
            "Restricted, Official",
            "Confidential, Restricted",
            "Secret, Confidential"
    })
    @DisplayName("🟥 should not mutate document without correct classification level")
    void shouldNotMutateDocumentWithoutCorrectClassificationLevel(
            String expectedClassificationLevel, String actualClassificationLevel) {
        // GIVEN
        final String documentName = "mutateDocumentClassificationLevel" + expectedClassificationLevel;
        final GraphQlTester.Request<?> request = testersMap
                .get("With Classification Level " + actualClassificationLevel)
                .documentName(documentName).variable("documentInput", TEST_DOCUMENT);
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.errors().satisfy(FORBIDDEN_ERROR_CHECKER);
        logger.info("shouldNotMutateDocumentWithoutCorrectClassificationLevel(): " +
                    "expectedClassificationLevel[{}], actualClassificationLevel[{}]",
                expectedClassificationLevel, actualClassificationLevel);
    }

    /**
     * Ensures that mutation does not occur when unauthorized.
     *
     * @param classificationLevel the classification level
     */
    @ParameterizedTest
    @CsvSource({"Official", "Restricted", "Confidential", "Secret"})
    @DisplayName("🟥 should not mutate document when unauthorized")
    void shouldNotMutateDocumentWhenUnauthorized(String classificationLevel) {
        // GIVEN
        final String documentName = "mutateDocumentClassificationLevel" + classificationLevel;
        final GraphQlTester.Request<?> request = testersMap.get("Without Authorization")
                .documentName(documentName).variable("documentInput", TEST_DOCUMENT);
        // WHEN
        final GraphQlTester.Response response = request.execute();
        // THEN
        Assertions.assertThat(response).isNotNull();
        response.errors().satisfy(UNAUTHORIZED_ERROR_CHECKER);
        logger.info("shouldNotMutateDocumentWhenUnauthorized(): classificationLevel[{}]", classificationLevel);
    }

    /**
     * Ensures that mutation does not occur with invalid credentials.
     *
     * @param classificationLevel the classification level
     */
    @ParameterizedTest
    @CsvSource({"Official", "Restricted", "Confidential", "Secret"})
    @DisplayName("🟥 should not mutate document with invalid credentials")
    void shouldNotMutateDocumentWithInvalidCredentials(String classificationLevel) {
        // GIVEN
        final String documentName = "mutateDocumentClassificationLevel" + classificationLevel;
        final GraphQlTester.Request<?> request = testersMap.get("With Invalid Credentials")
                .documentName(documentName).variable("documentInput", TEST_DOCUMENT);
        // WHEN / THEN
        Assertions.assertThatThrownBy(request::executeAndVerify).hasMessage(EXCEPTION_MSG_UNAUTHORIZED);
        logger.info("shouldNotMutateDocumentWithInvalidCredentials(): classificationLevel[{}]", classificationLevel);
    }
}
