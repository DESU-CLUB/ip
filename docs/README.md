# ByteBite User Guide

ByteBite is a desktop task manager application with a chat-based interface, designed to help you manage your tasks, deadlines, and events through natural conversation. ByteBite comes with both command-line and graphical user interfaces, making it versatile for different user preferences.

![ByteBite Interface](placeholder-for-screenshot.png)

## Quick Start

1. Ensure you have Java 11 or above installed
2. Download the latest `bytebite.jar`
3. Run the application:
   - For GUI: `java -jar bytebite.jar --gui`
   - For CLI: `java -jar bytebite.jar`

## Features

### Adding a Todo Task: `todo`

Creates a basic task without any specific deadline.

Format: `todo TASK_DESCRIPTION`

Example:

- `todo read chapter 3`

Expected output:

```
Got it. I've added this task:
[T][ ] read chapter 3
Now you have 1 task in the list.
```

### Adding a Deadline: `deadline`

Creates a task with a specific due date.

Format: `deadline TASK_DESCRIPTION /by YYYY-MM-DD`

Examples:

- `deadline complete assignment /by 2024-03-15`
- `deadline submit report /by 2024-04-01`

Expected output:

```
Got it. I've added this task:
[D][ ] complete assignment (by: Mar 15 2024)
Now you have 2 tasks in the list.
```

### Adding an Event: `event`

Creates an event that spans multiple days.

Format: `event EVENT_NAME /from YYYY-MM-DD /to YYYY-MM-DD`

Example:

- `event summer camp /from 2024-06-01 /to 2024-06-15`

Expected output:

```
Got it. I've added this task:
[E][ ] summer camp (from: Jun 1 2024 to: Jun 15 2024)
Now you have 3 tasks in the list.
```

### Listing Tasks: `list`

Shows all tasks currently in your task list.

Format: `list`

Expected output:

```
[T][ ] read chapter 3
[D][ ] complete assignment (by: Mar 15 2024)
[E][ ] summer camp (from: Jun 1 2024 to: Jun 15 2024)
```

### Finding Tasks by Keyword: `find`

Searches for tasks containing specific keywords in their descriptions.

Format: `find KEYWORD`

Example:

- `find assignment`

Expected output:

```
Here are the matching tasks in your list:

[D][ ] complete assignment (by: Mar 15 2024)
```

### Finding Tasks by Date: `finddate`

Searches for tasks scheduled on a specific date.

Format: `finddate YYYY-MM-DD`

Example:

- `finddate 2024-06-01`

Expected output:

```
Tasks on Jun 1 2024:

[E][ ] summer camp (from: Jun 1 2024 to: Jun 15 2024)
```

### Marking Tasks as Done: `mark`

Marks a task as completed.

Format: `mark TASK_NUMBER`

Example:

- `mark 1`

Expected output:

```
Nice! I've marked this task as done:
[T][X] read chapter 3
```

### Unmarking Tasks: `unmark`

Marks a completed task as not done.

Format: `unmark TASK_NUMBER`

Example:

- `unmark 1`

Expected output:

```
OK, I've marked this task as not done yet:
[T][ ] read chapter 3
```

### Deleting Tasks: `delete`

Removes a task from the list.

Format: `delete TASK_NUMBER`

Example:

- `delete 1`

Expected output:

```
Noted. I've removed this task:
[T][ ] read chapter 3
Now you have 2 tasks in the list.
```

### Getting Help: `help`

Shows the list of available commands and their forma:w:wts.

Format: `help`

### Exiting the Application: `bye`

Saves all tasks and exits the application.

Format: `bye`

## GUI Features

### Dark Mode Toggle

Click the moon/sun icon in the top-right corner to switch between light and dark modes.

### Chat Interface

- Tasks and commands are displayed in a chat-like interface
- Different commands have distinct color coding for better visibility
- Command responses include helpful visual indicators

### Auto-scrolling

The chat window automatically scrolls to show the latest messages and commands.

## Data Storage

ByteBite automatically saves your tasks to a file (`./data/tasks.txt`). This ensures your tasks persist between sessions.

## Command Format

- Words in `UPPER_CASE` are parameters you need to supply
- Parameters in square brackets `[]` are optional
- Dates must be in `YYYY-MM-DD` format (e.g., 2024-12-31)
- Task numbers start from 1 (not 0)

## Error Messages

ByteBite provides clear error messages when:

- A command format is invalid
- A task number doesn't exist
- A date is in the wrong format
- A task description is empty
- An event's end date is before its start date

Examples of error messages:

```
⚠️ Please provide a task number to mark
⚠️ Task 5 not found. Available tasks: 1 to 3
⚠️ Date must be in format: yyyy-MM-dd (e.g., 2024-12-31)
⚠️ The description of a todo cannot be empty!
⚠️ End time cannot be before start time
```

```
```

```
```

```
```

```
```
