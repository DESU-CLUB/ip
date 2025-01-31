// InvalidCommandException.java
package taskmanager.utils;

public class InvalidCommandException extends ByteBiteException {
    public InvalidCommandException(String command) {
        super("I don't understand '" + command + "'. Type 'help' for available commands.");
    }
}
