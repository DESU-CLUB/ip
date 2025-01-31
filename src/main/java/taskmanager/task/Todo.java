// Todo.java
package taskmanager.task;

/**
 * Represents a basic todo task without any deadline or timeframe.
 * This is the simplest task type that only contains a description
 * and completion status.
 */
public class Todo extends Task {
    /**
     * Creates a new todo task with the given description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
