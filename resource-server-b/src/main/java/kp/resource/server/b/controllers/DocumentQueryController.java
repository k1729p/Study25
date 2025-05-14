package kp.resource.server.b.controllers;

import kp.resource.server.domain.Document;
import kp.resource.server.services.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * The document query controller.
 */
@Controller
public class DocumentQueryController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final DocumentService documentService;

    /**
     * Parameterized constructor.
     *
     * @param documentService the service for the documents.
     */
    public DocumentQueryController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Query for the list of the documents.
     *
     * @return the list of the documents
     */
    @QueryMapping(name = "queryDocument")
    public List<Document> queryDocument() {

        final List<Document> documentList = documentService.getIdList().stream()
                .map(id -> new Document(id, "", "", "", ""))
                .toList();
        logger.info("queryDocument(): document list size[{}]", documentList.size());
        return documentList;
    }

    /**
     * Gets the official information
     *
     * @param document the document
     * @return the official information
     */
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
     * Gets the restricted information
     *
     * @param document the document
     * @return the restricted information
     */
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
     * Gets the confidential information
     *
     * @param document the document
     * @return the confidential information
     */
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
     * Gets the secret information
     *
     * @param document the document
     * @return the secret information
     */
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
