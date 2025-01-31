// TaskNotFoundException.java
package taskmanager.utils;

public class TaskNotFoundException extends ByteBiteException {
    public TaskNotFoundException(int taskNumber, int totalTasks) {
        super("Task " + taskNumber + " not found. Available tasks: "+ (totalTasks == 0 ? 0 : 1) + " to " + totalTasks);
    }
}
