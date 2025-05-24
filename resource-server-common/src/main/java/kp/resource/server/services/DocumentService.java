package kp.resource.server.services;

import kp.resource.server.domain.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service responsible for managing documents.
 */
@Service
public class DocumentService {
    private static final Map<String, Document> documentMap = new ConcurrentHashMap<>();

    /**
     * Recreates a document with the classification level set to official.
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
     * Recreates a document with the classification level set to restricted.
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
     * Recreates a document with the classification level set to confidential.
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
     * Recreates a document with the classification level set to secret.
     *
     * @param receivedDocument the received document
     */
    public void recreateDocumentClassificationLevelSecret(Document receivedDocument) {
        Optional.ofNullable(receivedDocument).map(Document::id)
                .ifPresent(id -> documentMap.put(id, receivedDocument));
    }

    /**
     * Deletes all documents.
     */
    public void deleteAllDocuments() {
        documentMap.clear();
    }

    /**
     * Retrieves the list of document ids.
     *
     * @return the list of document ids
     */
    public List<String> getIdList() {
        return documentMap.values().stream().map(Document::id).toList();
    }

    /**
     * Retrieves the official information for the given document id.
     *
     * @param id the document id
     * @return the official information for the given document id
     */
    public String getOfficialInformation(String id) {
        return Optional.ofNullable(documentMap.get(id))
                .map(Document::officialInformation).orElse("");
    }

    /**
     * Retrieves the restricted information for the given document id.
     *
     * @param id the document id
     * @return the restricted information for the given document id
     */
    public String getRestrictedInformation(String id) {
        return Optional.ofNullable(documentMap.get(id))
                .map(Document::restrictedInformation).orElse("");
    }

    /**
     * Retrieves the confidential information for the given document id.
     *
     * @param id the document id
     * @return the confidential information for the given document id
     */
    public String getConfidentialInformation(String id) {
        return Optional.ofNullable(documentMap.get(id))
                .map(Document::confidentialInformation).orElse("");
    }

    /**
     * Retrieves the secret information for the given document id.
     *
     * @param id the document id
     * @return the secret information for the given document id
     */
    public String getSecretInformation(String id) {
        return Optional.ofNullable(documentMap.get(id))
                .map(Document::secretInformation).orElse("");
    }

    /**
     * Adds a document for testing purposes only.
     *
     * @param id       the test document id
     * @param document the test document
     */
    public static void addDocumentForTesting(String id, Document document) {
        documentMap.put(id, document);
    }

}
