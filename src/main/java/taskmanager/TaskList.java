// TaskList.java
package taskmanager;
import java.util.ArrayList;
import java.time.LocalDate;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) throws TaskNotFoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException(index + 1, tasks.size());
        }
        return tasks.remove(index);
    }

    public Task getTask(int index) throws TaskNotFoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException(index + 1, tasks.size());
        }
        return tasks.get(index);
    }

    public ArrayList<Task> getTaskList() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

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
}
