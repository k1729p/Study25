package kp.resource.server.domain;

/**
 * The document with information classified at various levels: restricted, confidential, and secret.
 *
 * @param id                      the information id
 * @param officialInformation     the official information
 * @param restrictedInformation   the information with classification level "restricted"
 * @param confidentialInformation the information with classification level "confidential"
 * @param secretInformation       the information with classification level "secret"
 */
public record Document(String id,
                       String officialInformation,
                       String restrictedInformation,
                       String confidentialInformation,
                       String secretInformation) {
}
