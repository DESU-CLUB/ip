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
       System.out.println(ANSI_CYAN + LOGO + ANSI_RESET);
       printWithBorder("What can I do for you?");

       try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
           String input;
           while ((input = reader.readLine()) != null) {
               if (input.equalsIgnoreCase("bye")) {
                  printWithBorder(ANSI_CYAN + FAREWELL + ANSI_RESET);
                  break;
               }
              handleCommand(input);
           }
       } catch (Exception e) {
           System.err.println("Error reading input: " + e.getMessage());
       }
   }

   private void handleCommand(String input) {
       if (input.equalsIgnoreCase("list")) {
           showList();
       }  else if (input.startsWith("mark ")) {
            markTask(input, true);
       } else if (input.startsWith("unmark ")) {
            markTask(input, false);
       } else {
            tasks.add(new Task(input));
            printWithBorder("\tadded: " + input);
       }
   }

  private void markTask(String input, boolean isDone) {
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
             printWithBorder("Please provide a valid task number.");
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



   private void printWithBorder(String message) {
       System.out.println(BORDER);
       System.out.println(message);
       System.out.println(BORDER);
   }
}
