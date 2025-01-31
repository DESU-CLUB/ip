// InvalidFormatException.java
package taskmanager.utils;

/**
 * Exception thrown when command arguments are in an invalid format.
 */
public class InvalidFormatException extends ByteBiteException {
    /**
     * Creates a new InvalidFormatException with the specified message.
     *
     * @param message The error message explaining the format problem.
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
