package kp.client;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Contains constant values used across the application.
 */
public class Constants {
    /**
     * The window width.
     */
    public static final int WINDOW_WIDTH = 980;
    /**
     * The title.
     */
    public static final String TITLE = "GraphQL Client";
    /**
     * The resource directory.
     */
    public static final String RESOURCES_DIR = "src/main/resources";
    /**
     * The stylesheet name.
     */
    public static final String STYLESHEET_NAME = "/stylesheet.css";
    /**
     * The icon file name.
     */
    public static final String ICON_FILE_NAME = "/images/icon.gif";
    /**
     * The 'Mutate Documents' label.
     */
    public static final String MUTATE_DOCUMENTS_LABEL = "Mutate Documents";
    /**
     * The 'Delete All Documents' label.
     */
    public static final String DELETE_ALL_DOCUMENTS_LABEL = "Delete All Documents";
    /**
     * The 'Query Documents' label.
     */
    public static final String QUERY_DOCUMENTS_LABEL = "Query Documents";
    /**
     * The content text for the success alert.
     */
    public static final String ALERT_SUCCESS_FMT = """
            Endpoint: %s
            User: %s""";
    /**
     * The content text for the exception alert.
     */
    public static final String ALERT_EXCEPTION_FMT = """
            Endpoint: %s
            User: %s
            Exception:
            %s
            """;
    /**
     * The password ('clandestine' as bytes).
     */
    public static final Supplier<byte[]> PWD_BYTES = () -> new byte[]{
            99, 108, 97, 110, 100, 101, 115, 116, 105, 110, 101
    };
    /**
     * The endpoint url.
     */
    public static final List<String> ENDPOINT_URLS = List.of(
            "http://localhost:8082/graphql", "http://localhost:8083/graphql",
            "http://localhost:9092/graphql", "http://localhost:9093/graphql");
    /**
     * The endpoint label.
     */
    public static final List<String> ENDPOINT_LABELS = List.of(
            "Resource Server A - on Docker", "Resource Server B - on Docker",
            "Resource Server A - local", "Resource Server B - local");
    /**
     * The users.
     */
    public static final List<String> USERS = List.of("alice", "bob", "charlie", "david");
    /**
     * The user to classification level converter function.
     */
    public static final UnaryOperator<String> USER_TO_CLASSIFICATION_LEVEL_FUN = use ->
            switch (use) {
                case "alice" -> "Secret";
                case "bob" -> "Confidential";
                case "charlie" -> "Restricted";
                case "david" -> "Official";
                default -> "";
            };
    /**
     * The mutation document begin.
     */
    public static final String MUTATION_DOCUMENT_BEGIN = "mutation {\n";
    /**
     * The mutation document end.
     */
    public static final String MUTATION_DOCUMENT_END = "}";
    /**
     * The mutation document items function.
     */
    public static final IntFunction<BinaryOperator<String>> MUTATION_DOCUMENT_FUN =
            id -> (level, user) ->
                    """
                            item%1$d: mutateDocumentClassificationLevel%2$s(document : {
                                id : "%1$d"
                                officialInformation : "%3$s info updated by %4$s."
                                restrictedInformation : "%3$s info updated by %4$s."
                                confidentialInformation : "%3$s info updated by %4$s."
                                secretInformation : "%3$s info updated by %4$s."
                            }) {
                                success
                                id
                            }
                            """.formatted(id, level,
                            String.valueOf(id)
                                    .replaceAll("(.)(0)(.)", "𝓐$3")
                                    .replaceAll("(.)(1)(.)", "𝓑$3")
                                    .replaceAll("(.)(2)(.)", "𝓒$3")
                                    .replaceAll("(.)(3)(.)", "𝓓$3")
                                    .replaceAll("(.)(4)(.)", "𝓔$3")
                                    .replaceAll("(.)(5)(.)", "𝓕$3")
                                    .replaceAll("(.)(0)", "$1-０")
                                    .replaceAll("(.)(1)", "$1-１")
                                    .replaceAll("(.)(2)", "$1-２")
                                    .replaceAll("(.)(3)", "$1-３")
                                    .replaceAll("(.)(4)", "$1-４")
                                    .replaceAll("(.)(5)", "$1-５")
                                    .replaceAll("(.)(6)", "$1-６")
                                    .replaceAll("(.)(7)", "$1-７")
                                    .replaceAll("(.)(8)", "$1-８")
                                    .replaceAll("(.)(9)", "$1-９"),
                            Pattern.compile("(.)(.*)").matcher(user)
                                    .replaceAll(replacer ->
                                            replacer.group(1).toUpperCase() + replacer.group(2))
                    );
    /**
     * The mutation for deleting all documents.
     */
    public static final String MUTATION_DELETE_ALL_DOCUMENTS =
            """
                    mutation {
                        deleteAllDocuments {
                            success
                            id
                        }
                    }
                    """;
    /**
     * The query document.
     */
    public static final String QUERY_DOCUMENT =
            """
                    query {
                        queryDocument {
                            id
                            officialInformation
                            restrictedInformation
                            confidentialInformation
                            secretInformation
                        }
                    }
                    """;
    /**
     * The document property list.
     */
    public static final List<String> DOCUMENT_PROPERTY_LIST = List.of("id",
            "officialInformation", "restrictedInformation", "confidentialInformation", "secretInformation");
    /**
     * The document field name list.
     */
    public static final List<String> DOCUMENT_FIELD_NAME_LIST = List.of("Id",
            "Official Information", "Restricted Information", "Confidential Information", "Secret Information");
    /**
     * The document field style list.
     */
    public static final List<String> DOCUMENT_FIELD_STYLE_LIST = List.of("kp-document-label-id",
            "kp-document-label-official", "kp-document-label-restricted",
            "kp-document-label-confidential", "kp-document-label-secret");
    /**
     * The first document id.
     */
    public static final int FIRST_DOCUMENT_ID = 101;
    /**
     * the number of the documents.
     */
    public static final int DOCUMENTS_NUMBER = 20;
    /**
     * The mutation for deleting all documents.
     */
    public static final String BUTTON_CSS = "kp-button";

    /**
     * Private constructor to prevent instantiation.
     */
    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
