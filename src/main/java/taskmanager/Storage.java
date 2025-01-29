// Storage.java
package taskmanager;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Storage {
    private final Path filePath;
    private final Path directoryPath;

    public Storage(String directory, String filename) {
        this.directoryPath = Paths.get(directory);
        this.filePath = directoryPath.resolve(filename);
    }

    /**
     * Saves tasks to a file.
     * Format: TYPE | isDONE | DESCRIPTION | [DATE] | [END_DATE]
     * Example: T | 1 | do homework
     *          D | 0 | submit work | 2024-12-31
     *          E | 0 | meeting | 2024-12-31 | 2024-12-31
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
          .append(" | ").append(task.description);
        
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
     * Loads tasks from a file.
     * If file doesn't exist, returns an empty list.
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
                    if (parts.length < 4) throw new IllegalArgumentException("Invalid deadline format");
                    LocalDate deadlineDate = DateParser.parseDate(parts[3]);
                    task = new Deadline(description, deadlineDate);
                    break;
                case "E":
                    if (parts.length < 5) throw new IllegalArgumentException("Invalid event format");
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
     * Deletes the tasks file if it exists.
     * @return true if file was deleted or didn't exist, false if deletion failed
     */
    public boolean deleteTasksFile() {
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }
}
