// TaskNotFoundException.java
package taskmanager.utils;

/**
 * Exception thrown when attempting to access a task that doesn't exist.
 * Includes information about the requested task number and valid task range.
 */
public class TaskNotFoundException extends ByteBiteException {
    /**
     * Creates a new TaskNotFoundException with context about available tasks.
     *
     * @param taskNumber The task number that was requested (1-based).
     * @param totalTasks The total number of tasks currently in the list.
     */
    public TaskNotFoundException(int taskNumber, int totalTasks) {
        super("Task " + taskNumber + " not found. Available tasks: "+ (totalTasks == 0 ? 0 : 1) + " to " + totalTasks);
    }
}
