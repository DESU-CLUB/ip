// Exceptions.java
package taskmanager.utils;

public class ByteBiteException extends Exception {
    public ByteBiteException(String message) {
        super(message);
    }
}

public class EmptyDescriptionException extends ByteBiteException {
    public EmptyDescriptionException(String taskType) {
        super("The description of a " + taskType + " cannot be empty!");
    }
}

public class InvalidCommandException extends ByteBiteException {
    public InvalidCommandException(String command) {
        super("I don't understand '" + command + "'. Type 'help' for available commands.");
    }
}

public class InvalidFormatException extends ByteBiteException {
    public InvalidFormatException(String message) {
        super(message);
    }
}

public class TaskNotFoundException extends ByteBiteException {
    public TaskNotFoundException(int taskNumber, int totalTasks) {
        super("Task " + taskNumber + " not found. Available tasks: "+ (totalTasks == 0 ? 0 : 1) + " to " + totalTasks);
    }
}
