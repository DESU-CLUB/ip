import java.io.BufferedReader;
import java.io.InputStreamReader;

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
   private static final String FAREWELL = """
       🤖 *beep* *boop* 
       Powering down... Hope to see you again soon! 
       *whirring stops* 🤖
       """;
   
   public static void main(String[] args) {
       new ByteBite().start();
   }

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
               printWithBorder("\t"+input);
           }
       } catch (Exception e) {
           System.err.println("Error reading input: " + e.getMessage());
       }
   }

   private void printWithBorder(String message) {
       System.out.println(BORDER);
       System.out.println(message);
       System.out.println(BORDER);
   }
}
