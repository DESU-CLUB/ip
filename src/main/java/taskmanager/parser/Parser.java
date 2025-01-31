// Parser.java
package taskmanager.parser;

import taskmanager.command.*;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.InvalidCommandException;

/**
 * Parses user input strings into executable Command objects.
 * Handles all supported command types and their arguments.
 */
public class Parser {
    /**
     * Parses a user input string into a corresponding Command object.
     * The first word of the input is treated as the command type, and
     * the rest as command details.
     *
     * @param input The user input string to parse.
     * @return A Command object corresponding to the user input.
     * @throws InvalidCommandException If the command type is not recognized
     *         or if the input is empty.
     * @throws ByteBiteException If there is an error creating the command.
     */
    public Command parseCommand(String input) throws ByteBiteException {
        if (input.trim().isEmpty()) {
            throw new InvalidCommandException(input);
        }

        String[] parts = input.split(" ", 2);
        String commandType = parts[0].toLowerCase();
        String details = parts.length > 1 ? parts[1].trim() : "";

        return switch (commandType) {
            case "todo" -> new TodoCommand(details);
            case "deadline" -> new DeadlineCommand(details);
            case "event" -> new EventCommand(details);
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand(details, true);
            case "unmark" -> new MarkCommand(details, false);
            case "delete" -> new DeleteCommand(details);
            case "help" -> new HelpCommand();
            case "find" -> new FindCommand(details);
            default -> throw new InvalidCommandException(commandType);
        };
    }
}
