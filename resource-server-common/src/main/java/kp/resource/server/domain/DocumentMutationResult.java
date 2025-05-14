package kp.resource.server.domain;

/**
 * The result from the document mutation.
 *
 * @param success the success flag
 * @param id      the document id
 */
public record DocumentMutationResult(boolean success, String id) {
}
