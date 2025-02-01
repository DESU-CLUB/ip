// Storage.java
package taskmanager.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import taskmanager.parser.DateParser;
import taskmanager.task.Deadline;
import taskmanager.task.Event;
import taskmanager.task.Task;
import taskmanager.task.Todo;


/**
 * Handles persistence of task data to and from disk storage.
 * Uses a text-based file format to store tasks with their attributes.
 */
public class Storage {
    private final Path filePath;
    private final Path directoryPath;

    /**
     * Creates a new Storage instance with the specified directory and filename.
     *
     * @param directory The directory path for storing task data.
     * @param filename The name of the file to store tasks in.
     */
    public Storage(String directory, String filename) {
        this.directoryPath = Paths.get(directory);
        this.filePath = directoryPath.resolve(filename);
    }

    /**
     * Saves a list of tasks to the storage file.
     * Creates necessary directories if they don't exist.
     * Each task is stored in the format: TYPE | isDONE | DESCRIPTION | [DATE] | [END_DATE]
     *
     * @param tasks The list of tasks to save.
     * @throws IOException If there is an error writing to the file.
     */
    public void saveTasksToFile(ArrayList<Task> tasks) throws IOException {
        // Create directories if they don't exist
        Files.createDirectories(directoryPath);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(convertTaskToStorageFormat(task));
                writer.newLine();
            }
        }
    }

    /**
     * Converts a Task object to a string in the storage format.
     * The format is: TYPE | isDONE | DESCRIPTION | [DATE] | [END_DATE]
     *
     * @param task The Task object to convert.
     * @return The string representation of the task in storage format.
     */
    private String convertTaskToStorageFormat(Task task) {
        StringBuilder sb = new StringBuilder();
        // Add task type
        if (task instanceof Todo) {
            sb.append("T");
        } else if (task instanceof Deadline) {
            sb.append("D");
        } else if (task instanceof Event) {
            sb.append("E");
        }
        // Add done status and description
        sb.append(" | ").append(task.isDone() ? "1" : "0")
          .append(" | ").append(task.getDescription());
        // Add specific fields based on task type
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            sb.append(" | ").append(DateParser.formatForStorage(deadline.getDate()));
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append(" | ").append(DateParser.formatForStorage(event.getStartDate()))
              .append(" | ").append(DateParser.formatForStorage(event.getEndDate()));
        }
        return sb.toString();
    }

    /**
     * Loads tasks from the storage file.
     * Returns an empty list if the file doesn't exist.
     * Skips invalid lines in the file with a warning.
     *
     * @return The list of tasks loaded from storage.
     * @throws IOException If there is an error reading from the file.
     */
    public ArrayList<Task> loadTasksFromFile() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        // If file doesn't exist, return empty list
        if (!Files.exists(filePath)) {
            return tasks;
        }
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = convertStorageFormatToTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid line in storage file: " + line);
                }
            }
        }
        return tasks;
    }

    /**
     * Converts a line from the storage file to a Task object.
     * Throws an IllegalArgumentException if the line is invalid.
     *
     * @param line The line to convert.
     * @return The Task object created from the line.
     */
    private Task convertStorageFormatToTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid storage format");
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task;
        try {
            switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new IllegalArgumentException("Invalid deadline format");
                }
                LocalDate deadlineDate = DateParser.parseDate(parts[3]);
                task = new Deadline(description, deadlineDate);
                break;
            case "E":
                if (parts.length < 5) {
                    throw new IllegalArgumentException("Invalid event format");
                }
                LocalDate startDate = DateParser.parseDate(parts[3]);
                LocalDate endDate = DateParser.parseDate(parts[4]);
                if (!DateParser.isValidDateRange(startDate, endDate)) {
                    throw new IllegalArgumentException("Invalid date range in storage file");
                }
                task = new Event(description, startDate, endDate);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
            }

            if (isDone) {
                task.markAsDone();
            }

            return task;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error parsing task: " + e.getMessage());
        }
    }

    /**
     * Deletes the tasks storage file if it exists.
     *
     * @return true if the file was deleted or didn't exist, false if deletion failed.
     */
    public boolean deleteTasksFile() {
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }
}
