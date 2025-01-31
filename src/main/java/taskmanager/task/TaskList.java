// TaskList.java
package taskmanager.task;

import taskmanager.utils.TaskNotFoundException;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Manages a collection of tasks and provides operations for adding,
 * removing, and finding tasks. Supports different types of tasks
 * (Todo, Deadline, and Event) and provides search functionality
 * based on dates.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates a new empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    /**
     * Creates a task list initialized with the given tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task at the specified index.
     * Uses 0-based indexing.
     *
     * @param index The index of the task to delete.
     * @return The deleted task.
     * @throws TaskNotFoundException If the index is out of bounds.
     */
    public Task deleteTask(int index) throws TaskNotFoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException(index + 1, tasks.size());
        }
        return tasks.remove(index);
    }

    /**
     * Gets a task at the specified index.
     * Uses 0-based indexing.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws TaskNotFoundException If the index is out of bounds.
     */
    public Task getTask(int index) throws TaskNotFoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException(index + 1, tasks.size());
        }
        return tasks.get(index);
    }

    /**
     * Returns all tasks in this list.
     *
     * @return An ArrayList containing all tasks.
     */
    public ArrayList<Task> getTaskList() {
        return tasks;
    }

    /**
     * Returns the number of tasks in this list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if there are no tasks, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Finds all tasks scheduled for a specific date.
     * For deadlines, matches exact dates.
     * For events, matches if the date falls within the event period.
     * Todo tasks are never included in the results.
     *
     * @param targetDate The date to search for.
     * @return A list of all tasks scheduled for the target date.
     */
    public ArrayList<Task> findTasksOnDate(LocalDate targetDate) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getDate().equals(targetDate)) {
                    matchingTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                LocalDate startDate = event.getStartDate();
                LocalDate endDate = event.getEndDate();
                if (!startDate.isAfter(targetDate) && !endDate.isBefore(targetDate)) {
                    matchingTasks.add(task);
                }
            }
        }
        return matchingTasks;
    }

  /**
   * Finds all tasks whose descriptions contain the given keyword (case-insensitive).
   *
   * @param keyword The keyword to search for.
   * @return A list of tasks containing the keyword in their descriptions.
   */
  public ArrayList<Task> findTasksByKeyword(String keyword) {
      ArrayList<Task> matchingTasks = new ArrayList<>();
      String lowercaseKeyword = keyword.toLowerCase();
        
      for (Task task : tasks) {
          if (task.getDescription().toLowerCase().contains(lowercaseKeyword)) {
                matchingTasks.add(task);
          }
      }
      return matchingTasks;
  }

}
