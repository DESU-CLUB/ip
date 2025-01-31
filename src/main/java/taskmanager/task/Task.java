// Task.java
package taskmanager.task;

/**
 * Represents a basic task in the task management system.
 * This is the base class for all task types and provides common functionality
 * for task description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description.
     * All tasks are initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
       this.description = description;
       this.isDone = false;
    }  

    /**
     * Returns the description of this task.
     *
     * @return The task description.
     */
    public String getDescription() { return description; }

    /**
     * Marks this task as complete.
     */
    public void markAsDone() { isDone = true; }

    /**
     * Marks this task as not complete.
     */
    public void unmark() { isDone = false; }

    /**
     * Returns whether this task is marked as done.
     *
     * @return true if the task is complete, false otherwise.
     */
    public boolean isDone() { return isDone; }

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : " ", description);
    }
}
