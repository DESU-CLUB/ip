package taskmanager;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Storage {
    private final Path filePath;
    private final Path directoryPath;

    public Storage(String directory, String filename) {
        this.directoryPath = Paths.get(directory);
        this.filePath = directoryPath.resolve(filename);
    }

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

    public boolean deleteTasksFile() {
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
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
            sb.append(" | ").append(((Deadline) task).by);
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append(" | ").append(event.startTime)
              .append(" | ").append(event.endTime);
        }
        
        return sb.toString();
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

        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) throw new IllegalArgumentException("Invalid deadline format");
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                if (parts.length < 5) throw new IllegalArgumentException("Invalid event format");
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
