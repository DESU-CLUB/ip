package taskmanager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
              break;

          case "delete":
              if (details.isEmpty()) {
                  throw new InvalidFormatException("Please provide a task number to delete");
              }
              deleteTask(input);
              break;    

          case "help":
              showHelp();
              break;
              
          default:
              throw new InvalidCommandException(command);
      }
  }

  private void showHelp() {
      String help = """
          Available commands:
          todo <task>
          deadline <task> /by <date>
          event <name> /from <start> /to <end>
          list
          mark <task number>
          unmark <task number>
          delete <task number>
          bye
          """;
      printWithBorder(help);
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
