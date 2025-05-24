package kp.resource.server.b.controllers;

import kp.resource.server.domain.Document;
import kp.resource.server.domain.DocumentMutationResult;
import kp.resource.server.services.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;

/**
 * Controller responsible for handling document mutation operations.
 */
@Controller
public class DocumentMutationController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final DocumentService documentService;

    /**
     * Parameterized constructor.
     *
     * @param documentService the service used for document operations
     */
    public DocumentMutationController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Mutates a document with classification level 'official'.
     *
     * @param document the document to mutate
     * @return the result of the mutation containing the mutated document id
     */
    @MutationMapping(name = "mutateDocumentClassificationLevelOfficial")
    public DocumentMutationResult mutateDocumentClassificationLevelOfficial(
            @Argument("document") Document document) {

        documentService.recreateDocumentClassificationLevelOfficial(document);
        logger.info("""
                        mutateDocumentClassificationLevelOfficial(): id[{}],
                        \t officialInformation[{}],
                        \t restrictedInformation[{}],
                        \t confidentialInformation[{}],
                        \t secretInformation[{}]""",
                document.id(), document.officialInformation(), document.restrictedInformation(),
                document.confidentialInformation(), document.secretInformation());
        return new DocumentMutationResult(true, document.id());
    }

    /**
     * Mutates a document with classification level 'restricted'.
     *
     * @param document the document to mutate
     * @return the result of the mutation containing the mutated document id
     */
    @MutationMapping(name = "mutateDocumentClassificationLevelRestricted")
    public DocumentMutationResult mutateDocumentClassificationLevelRestricted(
            @Argument("document") Document document) {

        documentService.recreateDocumentClassificationLevelRestricted(document);
        logger.info("""
                        mutateDocumentClassificationLevelRestricted(): id[{}],
                        \t officialInformation[{}],
                        \t restrictedInformation[{}],
                        \t confidentialInformation[{}],
                        \t secretInformation[{}]""",
                document.id(), document.officialInformation(), document.restrictedInformation(),
                document.confidentialInformation(), document.secretInformation());
        return new DocumentMutationResult(true, document.id());
    }

    /**
     * Mutates a document with classification level 'confidential'.
     *
     * @param document the document to mutate
     * @return the result of the mutation containing the mutated document id
     */
    @MutationMapping(name = "mutateDocumentClassificationLevelConfidential")
    public DocumentMutationResult mutateDocumentClassificationLevelConfidential(
            @Argument("document") Document document) {

        documentService.recreateDocumentClassificationLevelConfidential(document);
        logger.info("""
                        mutateDocumentClassificationLevelConfidential(): id[{}],
                        \t officialInformation[{}],
                        \t restrictedInformation[{}],
                        \t confidentialInformation[{}],
                        \t secretInformation[{}]""",
                document.id(), document.officialInformation(), document.restrictedInformation(),
                document.confidentialInformation(), document.secretInformation());
        return new DocumentMutationResult(true, document.id());
    }

    /**
     * Mutates a document with classification level 'secret'.
     *
     * @param document the document to mutate
     * @return the result of the mutation containing the mutated document id
     */
    @MutationMapping(name = "mutateDocumentClassificationLevelSecret")
    public DocumentMutationResult mutateDocumentClassificationLevelSecret(
            @Argument("document") Document document) {

        documentService.recreateDocumentClassificationLevelSecret(document);
        logger.info("""
                        mutateDocumentClassificationLevelSecret(): id[{}],
                        \t officialInformation[{}],
                        \t restrictedInformation[{}],
                        \t confidentialInformation[{}],
                        \t secretInformation[{}]""",
                document.id(), document.officialInformation(), document.restrictedInformation(),
                document.confidentialInformation(), document.secretInformation());
        return new DocumentMutationResult(true, document.id());
    }

    /**
     * Deletes all documents.
     *
     * @return the result of the mutation
     */
    @MutationMapping(name = "deleteAllDocuments")
    public DocumentMutationResult deleteAllDocuments() {

        documentService.deleteAllDocuments();
        logger.info("deleteAllDocuments():");
        return new DocumentMutationResult(true);
    }
}
