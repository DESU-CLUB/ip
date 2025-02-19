// Task.java
package taskmanager.task;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a basic task in the task management system.
 * This is the base class for all task types and provides common functionality
 * for task description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected Set<String> tags = new HashSet<>();

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
    public String getDescription() {
        return description;
    }

    /**
     * Marks this task as complete.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not complete.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return true if the task is complete, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    public void addTag(String tag){
      if (tag.startsWith("#")) {
        tags.add(tag);
      } else {
        tags.add("#" + tag);
      }
    }

    public void removeTag(String tag) {
      String normalizedTag = tag.startsWith("#") ? tag : "#" + tag;
      tags.remove(normalizedTag);
    }

    public Set<String> getTags() {
      return new HashSet<>(tags);
    }

    @Override
    public String toString() {
        String tagString = tags.isEmpty() ? "" : " " + tags.stream()
                .sorted()
                .collect(Collectors.joining(" "));
        return String.format("[%s] %s%s", isDone ? "X" : " ", description, tagString);
    }
}
