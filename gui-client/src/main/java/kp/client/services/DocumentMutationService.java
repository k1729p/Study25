package kp.client.services;

import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.web.client.RestClient;

import java.util.stream.IntStream;

import static kp.client.Constants.*;

/**
 * The service for mutations using the GraphQL client.
 */
public class DocumentMutationService {

    /**
     * Mutates the documents.
     *
     * @param endpoint the endpoint
     * @param user     the user
     */
    public void mutateDocuments(String endpoint, String user) {

        createGraphQlClient(endpoint, user)
                .document(createRequestDocument(user)).executeSync();
    }

    /**
     * Deletes all documents.
     *
     * @param endpoint the endpoint
     * @param user     the user
     */
    public void deleteAllDocuments(String endpoint, String user) {

        createGraphQlClient(endpoint, user)
                .document(MUTATION_DELETE_ALL_DOCUMENTS).executeSync();
    }

    /**
     * Creates the document for the GraphQL client.
     *
     * @param user the user
     * @return the document for the GraphQL client
     */
    private String createRequestDocument(String user) {

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MUTATION_DOCUMENT_BEGIN);
        IntStream.range(FIRST_DOCUMENT_ID, FIRST_DOCUMENT_ID + DOCUMENTS_NUMBER)
                .mapToObj(id -> MUTATION_DOCUMENT_FUN.apply(id)
                        .apply(USER_TO_CLASSIFICATION_LEVEL_FUN.apply(user), user))
                .forEach(stringBuilder::append);
        stringBuilder.append(MUTATION_DOCUMENT_END);
        return stringBuilder.toString();
    }

    /**
     * Creates the HttpSyncGraphQlClient with basic authentication.
     *
     * @param endpoint the endpoint
     * @param user     the user
     * @return the configured HttpSyncGraphQlClient
     */
    private HttpSyncGraphQlClient createGraphQlClient(String endpoint, String user) {

        return HttpSyncGraphQlClient
                .builder(RestClient.create(endpoint))
                .headers(headers -> headers.setBasicAuth(user, new String(PWD_BYTES.get())))
                .build();
    }

}
