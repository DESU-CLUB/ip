// EmptyDescriptionException.java
package taskmanager.utils;

/**
 * Exception thrown when attempting to create a task with an empty description.
 */
public class EmptyDescriptionException extends ByteBiteException {
    /**
     * Creates a new EmptyDescriptionException for the specified task type.
     *
     * @param taskType The type of task (todo, deadline, or event).
     */
    public EmptyDescriptionException(String taskType) {
        super("The description of a " + taskType + " cannot be empty!");
    }
}
