package atlas;

/**
 * Represents application-specific exceptions in Atlas.
 */
public class AtlasException extends Exception {

    /**
     * Constructs an AtlasException with the given error message.
     *
     * @param message Error description
     */
    public AtlasException(String message) {
        super(message);
    }
}
