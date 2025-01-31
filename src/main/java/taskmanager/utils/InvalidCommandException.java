// InvalidCommandException.java
package taskmanager.utils;

/**
 * Exception thrown when an invalid or unrecognized command is entered.
 */
public class InvalidCommandException extends ByteBiteException {
    /**
     * Creates a new InvalidCommandException for the specified command.
     *
     * @param command The invalid command that was entered.
     */
    public InvalidCommandException(String command) {
        super("I don't understand '" + command + "'. Type 'help' for available commands.");
    }
}
