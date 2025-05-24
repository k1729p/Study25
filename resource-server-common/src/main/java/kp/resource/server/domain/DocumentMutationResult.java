package kp.resource.server.domain;

/**
 * Represents the result from the document mutation.
 *
 * @param success the status of the mutation operation
 * @param id      the unique identifier of the document
 */
public record DocumentMutationResult(boolean success, String id) {
    /**
     * Parameterized constructor.
     *
     * @param success the success flag
     */
    public DocumentMutationResult(boolean success) {
        this(success, "");
    }
}
