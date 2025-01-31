// Task.java
package taskmanager.task;

public class Task {
   protected String description;
   protected boolean isDone;

   public Task(String description) {
       this.description = description;
       this.isDone = false;
   }

   public String getDescription() { return description; }
   public void markAsDone() { isDone = true; }
   public void unmark() { isDone = false; }
   public boolean isDone() { return isDone; }

   @Override
   public String toString() {
       return String.format("[%s] %s", isDone ? "X" : " ", description);
   }
}
