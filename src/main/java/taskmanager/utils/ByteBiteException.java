// ByteBiteException.java
package taskmanager.utils;

/**
 * Base exception class for all application-specific exceptions.
 * Provides a common ancestor for all custom exceptions in the system.
 */
public class ByteBiteException extends Exception {
    /**
     * Creates a new ByteBiteException with the specified message.
     *
     * @param message The error message.
     */
    public ByteBiteException(String message) {
        super(message);
    }
}
