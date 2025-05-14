package kp.resource.server.services;

import kp.resource.server.domain.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The service responsible for retrieving documents.
 */
@Service
public class DocumentService {
    private static final Map<String, Document> documentMap = new HashMap<>();

    /**
     * Recreates the document with classification level official.
     *
     * @param receivedDocument the received document
     */
    public void recreateDocumentClassificationLevelOfficial(Document receivedDocument) {

        final Optional<String> idOpt = Optional.ofNullable(receivedDocument).map(Document::id);
        if (idOpt.isEmpty()) {
            return;
        }
        final String id = idOpt.get();
        final Optional<Document> shieldedDocumentOpt = idOpt.map(documentMap::get);
        if (shieldedDocumentOpt.isEmpty()) {
            documentMap.put(id, receivedDocument);
            return;
        }
        final Document shieldedDocument = shieldedDocumentOpt.get();
        documentMap.put(id, new Document(id,
                Optional.of(receivedDocument)
                        .map(Document::officialInformation).orElse(""),
                shieldedDocument.restrictedInformation(),
                shieldedDocument.confidentialInformation(),
                shieldedDocument.secretInformation())
        );
    }

    /**
     * Recreates the document with classification level restricted.
     *
     * @param receivedDocument the received document
     */
    public void recreateDocumentClassificationLevelRestricted(Document receivedDocument) {

        final Optional<String> idOpt = Optional.ofNullable(receivedDocument).map(Document::id);
        if (idOpt.isEmpty()) {
            return;
        }
        final String id = idOpt.get();
        final Optional<Document> shieldedDocumentOpt = idOpt.map(documentMap::get);
        if (shieldedDocumentOpt.isEmpty()) {
            documentMap.put(id, receivedDocument);
            return;
        }
        final Document shieldedDocument = shieldedDocumentOpt.get();
        documentMap.put(id, new Document(id,
                Optional.of(receivedDocument)
                        .map(Document::officialInformation).orElse(""),
                Optional.of(receivedDocument)
                        .map(Document::restrictedInformation).orElse(""),
                shieldedDocument.confidentialInformation(),
                shieldedDocument.secretInformation())
        );
    }

    /**
     * Recreates the document with classification level confidential.
     *
     * @param receivedDocument the received document
     */
    public void recreateDocumentClassificationLevelConfidential(Document receivedDocument) {

        final Optional<String> idOpt = Optional.ofNullable(receivedDocument).map(Document::id);
        if (idOpt.isEmpty()) {
            return;
        }
        final String id = idOpt.get();
        final Optional<Document> shieldedDocumentOpt = idOpt.map(documentMap::get);
        if (shieldedDocumentOpt.isEmpty()) {
            documentMap.put(id, receivedDocument);
            return;
        }
        final Document shieldedDocument = shieldedDocumentOpt.get();
        documentMap.put(id, new Document(id,
                Optional.of(receivedDocument)
                        .map(Document::officialInformation).orElse(""),
                Optional.of(receivedDocument)
                        .map(Document::restrictedInformation).orElse(""),
                Optional.of(receivedDocument)
                        .map(Document::confidentialInformation).orElse(""),
                shieldedDocument.secretInformation())
        );
    }

    /**
     * Recreates the document with classification level secret.
     *
     * @param receivedDocument the received document
     */
    public void recreateDocumentClassificationLevelSecret(Document receivedDocument) {

        Optional.ofNullable(receivedDocument).map(Document::id)
                .ifPresent(id -> documentMap.put(id, receivedDocument));
    }

    /**
     * Gets the list with the document ids
     *
     * @return the list with the document ids
     */
    public List<String> getIdList() {
        return documentMap.values().stream().map(Document::id).toList();
    }

    /**
     * Gets the official information
     *
     * @param id the information id
     * @return the official information
     */
    public String getOfficialInformation(String id) {
        return Optional.ofNullable(documentMap.get(id))
                .map(Document::officialInformation).orElse("");
    }

    /**
     * Gets the restricted information
     *
     * @param id the information id
     * @return the restricted information
     */
    public String getRestrictedInformation(String id) {
        return Optional.ofNullable(documentMap.get(id))
                .map(Document::restrictedInformation).orElse("");
    }

    /**
     * Gets the confidential information
     *
     * @param id the information id
     * @return the confidential information
     */
    public String getConfidentialInformation(String id) {
        return Optional.ofNullable(documentMap.get(id))
                .map(Document::confidentialInformation).orElse("");
    }

    /**
     * Gets the secret information
     *
     * @param id the information id
     * @return the secret information
     */
    public String getSecretInformation(String id) {
        return Optional.ofNullable(documentMap.get(id))
                .map(Document::secretInformation).orElse("");
    }

    /**
     * Adds document for testing. It is used only for tests.
     *
     * @param id       the test document id
     * @param document the test document
     */
    public static void addDocumentForTesting(String id,
                                             Document document) {
        documentMap.put(id, document);
    }

}
