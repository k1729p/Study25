package kp.client.services;

import kp.client.model.DocumentProperties;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.ClientResponseField;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.web.client.RestClient;

import java.util.*;

import static kp.client.Constants.PWD_BYTES;
import static kp.client.Constants.QUERY_DOCUMENT;

/**
 * Service for executing queries using the GraphQL client.
 */
public class DocumentQueryService {
    /**
     * Queries for the list of documents.
     *
     * @param endpoint the endpoint
     * @param user     the username
     * @return the list of documents
     */
    public List<DocumentProperties> queryDocuments(String endpoint, String user) {

        final HttpSyncGraphQlClient graphQlClient = HttpSyncGraphQlClient
                .builder(RestClient.create(endpoint))
                .headers(headers -> headers.setBasicAuth(user, new String(PWD_BYTES.get())))
                .build();
        return convertToDocumentPropertiesList(graphQlClient.document(QUERY_DOCUMENT).executeSync());
    }

    /**
     * Converts the GraphQL response to a list of DocumentProperties.
     *
     * @param response the response from the GraphQL client
     * @return the list of documents
     */
    private List<DocumentProperties> convertToDocumentPropertiesList(ClientGraphQlResponse response) {

        final List<DocumentProperties> documentList = new ArrayList<>();
        final List<?> list = Optional.of(response.field("queryDocument"))
                .map(ClientResponseField::getValue)
                .filter(List.class::isInstance)
                .map(List.class::cast)
                .orElseGet(Collections::emptyList);
        list.forEach(item -> Optional.of(item)
                .filter(Map.class::isInstance)
                .map(Map.class::cast)
                .map(DocumentProperties::new)
                .ifPresent(documentList::add));
        documentList.sort(Comparator.comparing(props -> props.id().get()));
        return documentList;
    }
}
