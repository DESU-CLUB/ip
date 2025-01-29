package taskmanager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ByteBite {
  private static final String LOGO = """
          ____  ____
        | __ )| __ )
        |  _ \\|  _ \\
        | |_) | |_) |
        |____/|____/
          """;
          
  private static final String ANSI_CYAN = "\u001B[36m";
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String BORDER = "─".repeat(50);
  private ArrayList<Task> tasks = new ArrayList<>();
  private final Storage storage;

  public ByteBite(){
      storage = new Storage("./data", "tasks.txt");
      loadTasks();
  }

  private void loadTasks() {
    try {
      tasks = storage.loadTasksFromFile();
      if (!tasks.isEmpty()){
          printWithBorder("Loaded " + tasks.size() + " tasks from storage");
      }
    } catch (IOException e) {
      printWithBorder("Error loading tasks from storage: " + e.getMessage());
      handleCorruptedFile(e);
    }
  }

  private void handleCorruptedFile(IOException error) {
    printWithBorder("⚠️ Error detected in tasks file: " + error.getMessage());
    try {
        // Delete the corrupted file
        if (storage.deleteTasksFile()) {
            printWithBorder("Corrupted tasks file has been removed");
            // Initialize empty task list
            tasks = new ArrayList<>();
            // Create a fresh storage file
            storage.saveTasksToFile(tasks);
            printWithBorder("Created new empty tasks file");
        } else {
            printWithBorder("❌ Unable to remove corrupted file - please check file permissions");
        }
    } catch (IOException e) {
        printWithBorder("❌ Critical error handling corrupted file: " + e.getMessage());
    }
  }

  private void saveTasks() {
      try {
          storage.saveTasksToFile(tasks);
      } catch (IOException e) {
          printWithBorder("Error saving tasks: " + e.getMessage());
        }
    }

  private static final String FAREWELL = """
      🤖 *beep* *boop* 
      Powering down... Hope to see you again soon! 
      *whirring stops* 🤖
      """;
  

  public void start() {
      printWithBorder(ANSI_CYAN + "Hello! I'm ByteBite" + ANSI_RESET);
      System.out.print(ANSI_CYAN + LOGO + ANSI_RESET);
      printWithBorder("Type 'help' to see available commands");

      try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
          String input;
          while ((input = reader.readLine()) != null) {
              if (input.equalsIgnoreCase("bye")) {
                  printWithBorder(ANSI_CYAN + FAREWELL + ANSI_RESET);
                  break;
              }
              try {
                  handleCommand(input.trim());
              } catch (ByteBiteException e) {
                  printWithBorder("⚠️ " + e.getMessage());
              }
          }
      } catch (Exception e) {
          System.err.println("Error reading input: " + e.getMessage());
      }
  }


  private void handleCommand(String input) throws ByteBiteException {
      if (input.trim().isEmpty()) {
          throw new InvalidCommandException(input);
      }

      String[] parts = input.split(" ", 2);
      String command = parts[0].toLowerCase();
      String details = parts.length > 1 ? parts[1].trim() : "";

      switch (command) {
          case "todo":
              if (details.isEmpty()) {
                  throw new EmptyDescriptionException("todo");
              }
              Task todo = new Todo(details);
              tasks.add(todo);
              printTaskAdded(todo);
              saveTasks();
              break;
              
          case "deadline":
              if (details.isEmpty()) {
                  throw new EmptyDescriptionException("deadline");
              }
              String[] deadlineParts = details.split(" /by ", 2);
              if (deadlineParts.length != 2 || deadlineParts[0].trim().isEmpty()) {
                  throw new InvalidFormatException("Please use format: deadline <task> /by <date>");
              }
              Task deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
              tasks.add(deadline);
              printTaskAdded(deadline);
              saveTasks();
              break;
              
          case "event":
              if (details.isEmpty()) {
                  throw new EmptyDescriptionException("event");
              }
              String[] eventParts = details.split(" /from ", 2);
              if (eventParts.length != 2 || eventParts[0].trim().isEmpty()) {
                  throw new InvalidFormatException("Please use format: event <name> /from <start> /to <end>");
              }
              String[] timeParts = eventParts[1].split(" /to ", 2);
              if (timeParts.length != 2) {
                  throw new InvalidFormatException("Please use format: event <name> /from <start> /to <end>");
              }
              Task event = new Event(eventParts[0], timeParts[0], timeParts[1]);
              tasks.add(event);
              printTaskAdded(event);
              saveTasks();
              break;
              
          case "list":
              showList();
              break;
              
          case "mark":
          case "unmark":
              if (details.isEmpty()) {
                  throw new InvalidFormatException("Please provide a task number to " + command);
              }
              markTask(input, command.equals("mark"));
              saveTasks();
              break;

          case "delete":
              if (details.isEmpty()) {
                  throw new InvalidFormatException("Please provide a task number to delete");
              }
              deleteTask(input);
              saveTasks();
              break;    

          case "help":
              showHelp();
              break;

          case "find":
              if (details.isEmpty()) {
                  throw new InvalidFormatException("Please provide a date to find tasks");
              }

              findTasksOnDate(details);
              break;
                        
              
          default:
              throw new InvalidCommandException(command);
      }
  }

   

  private void showHelp() {
        String help = """
            Available commands:
            todo <task>
            deadline <task> /by yyyy-MM-dd
            event <name> /from yyyy-MM-dd /to yyyy-MM-dd
            list
            find <yyyy-MM-dd>
            mark <task number>
            unmark <task number>
            delete <task number>
            bye
            
            Date format: yyyy-MM-dd (e.g., 2024-12-31 for Dec 31 2024)
            """;
        printWithBorder(help);
    }

    private void findTasksOnDate(String dateStr) {
        try {
            LocalDate targetDate = LocalDate.parse(dateStr);
            StringBuilder results = new StringBuilder("Tasks on " + 
                targetDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":\n");
            
            boolean found = false;
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                boolean matches = false;
                
                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    matches = deadline.getDate().equals(targetDate);
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    LocalDate startDate = event.getStartDate();
                    LocalDate endDate = event.getEndDate();
                    matches = (!startDate.isAfter(targetDate) && !endDate.isBefore(targetDate));
                }
                
                if (matches) {
                    found = true;
                    results.append(String.format("%d. %s%n", i + 1, task));
                }
            }
            
            if (!found) {
                results.append("No tasks found on this date.");
            }
            
            printWithBorder(results.toString().trim());
        } catch (DateTimeParseException e) {
            printWithBorder(" ⚠️ Please use the format yyyy-MM-dd (e.g., 2024-12-31)");
        }
    }

   private void printTaskAdded(Task task) {
      StringBuilder message = new StringBuilder("Got it. I've added this task:\n");
      message.append("  ").append(task).append("\n");
      message.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
      printWithBorder(message.toString());
  }

  private void markTask(String input, boolean isDone) throws TaskNotFoundException {
       try {
           int index = Integer.parseInt(input.split(" ")[1]) - 1;
           if (index >= 0 && index < tasks.size()) {
               Task task = tasks.get(index);
               if (isDone) {
                   task.markAsDone();
                   printWithBorder("Nice! I've marked this task as done:\n  " + task);
               } else {
                   task.unmark();
                   printWithBorder("OK, I've marked this task as not done yet:\n  " + task);
               }
           } else {
              throw new TaskNotFoundException(index, tasks.size());
           }
       } catch (NumberFormatException | IndexOutOfBoundsException e) {
           printWithBorder("Please provide a valid task number.");
       }
   }

  private void showList() {
       StringBuilder list = new StringBuilder();
       for (int i = 0; i < tasks.size(); i++) {
           list.append(String.format("%d. %s%n", i + 1, tasks.get(i)));
       }
       printWithBorder(list.toString().trim());
   }


  private void deleteTask(String input) throws ByteBiteException {
    try {
        int taskNumber = Integer.parseInt(input.split(" ")[1]);
        int index = taskNumber - 1;
        
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException(taskNumber, tasks.size());
        }
        
        Task deletedTask = tasks.remove(index);
        String message = "Noted. I've removed this task:\n  " + deletedTask + 
                        "\nNow you have " + tasks.size() + " tasks in the list.";
        printWithBorder(message);
    } catch (NumberFormatException e) {
        throw new InvalidFormatException("Please provide a valid task number");
    }
  }



  private void printWithBorder(String message) {
       System.out.println(BORDER);
       System.out.println(message);
       System.out.println(BORDER);
   }
}
