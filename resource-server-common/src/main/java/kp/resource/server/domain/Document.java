package kp.resource.server.domain;

/**
 * Represents a document containing information classified at various levels.
 * <p>
 * There are four classification levels:
 * </p>
 * <ul>
 *     <li>Official</li>
 *     <li>Restricted</li>
 *     <li>Confidential</li>
 *     <li>Secret</li>
 * </ul>
 *
 * @param id                      the unique document identifier
 * @param officialInformation     information classified as "official"
 * @param restrictedInformation   information classified as "restricted"
 * @param confidentialInformation information classified as "confidential"
 * @param secretInformation       information classified as "secret"
 */
public record Document(
        String id,
        String officialInformation,
        String restrictedInformation,
        String confidentialInformation,
        String secretInformation) {
}