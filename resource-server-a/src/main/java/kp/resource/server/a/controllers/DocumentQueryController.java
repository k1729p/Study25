package kp.resource.server.a.controllers;

import kp.resource.server.domain.Document;
import kp.resource.server.services.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static kp.resource.server.Constants.*;

/**
 * Controller responsible for handling document query operations.
 */
@Controller
public class DocumentQueryController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final DocumentService documentService;

    /**
     * Parameterized constructor.
     *
     * @param documentService the service used for document operations
     */
    public DocumentQueryController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Retrieves the list of documents.
     *
     * @return the list of documents
     */
    @PreAuthorize("hasRole('" + LEVEL_OFFICIAL + "')")
    @QueryMapping(name = "queryDocument")
    public List<Document> queryDocument() {

        final List<Document> documentList = documentService.getIdList().stream()
                .map(id -> new Document(id, "", "", "", ""))
                .toList();
        logger.info("queryDocument(): document list size[{}]", documentList.size());
        return documentList;
    }

    /**
     * Retrieves information classified as 'official' for the specified document.
     *
     * @param document the document for which to retrieve official information
     * @return the official information
     */
    @PreAuthorize("hasRole('" + LEVEL_OFFICIAL + "')")
    @SchemaMapping(field = "officialInformation")
    public String getOfficialInformation(Document document) {

        final String officialInformation = documentService.getOfficialInformation(document.id());
        logger.info("""
                        getOfficialInformation(): id[{}],
                        \t officialInformation[{}]""",
                document.id(), officialInformation);
        return officialInformation;
    }

    /**
     * Retrieves information classified as 'restricted' for the specified document.
     *
     * @param document the document for which to retrieve restricted information
     * @return the restricted information
     */
    @PreAuthorize("hasRole('" + LEVEL_RESTRICTED + "')")
    @SchemaMapping(field = "restrictedInformation")
    public String getRestrictedInformation(Document document) {

        final String restrictedInformation = documentService.getRestrictedInformation(document.id());
        logger.info("""
                        getRestrictedInformation(): id[{}],
                        \t restrictedInformation[{}]""",
                document.id(), restrictedInformation);
        return restrictedInformation;
    }

    /**
     * Retrieves information classified as 'confidential' for the specified document.
     *
     * @param document the document for which to retrieve confidential information
     * @return the confidential information
     */
    @PreAuthorize("hasRole('" + LEVEL_CONFIDENTIAL + "')")
    @SchemaMapping(field = "confidentialInformation")
    public String getConfidentialInformation(Document document) {

        final String confidentialInformation = documentService.getConfidentialInformation(document.id());
        logger.info("""
                        getConfidentialInformation(): id[{}],
                        \t confidentialInformation[{}]""",
                document.id(), confidentialInformation);
        return confidentialInformation;
    }

    /**
     * Retrieves information classified as 'secret' for the specified document.
     *
     * @param document the document for which to retrieve secret information
     * @return the secret information
     */
    @PreAuthorize("hasRole('" + LEVEL_SECRET + "')")
    @SchemaMapping(field = "secretInformation")
    public String getSecretInformation(Document document) {

        final String secretInformation = documentService.getSecretInformation(document.id());
        logger.info("""
                        getSecretInformation(): id[{}],
                        \t secretInformation[{}]""",
                document.id(), secretInformation);
        return secretInformation;
    }
}