package taskmanager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.task.TaskList;
import taskmanager.task.Task;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.EmptyDescriptionException;
import taskmanager.utils.InvalidFormatException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeadlineCommandTest {
    private TaskList taskList;
    private Ui ui;

    @BeforeEach
    void setUp() {
        taskList = mock(TaskList.class);
        ui = mock(Ui.class);
    }

    @Test
    void executeValidDeadlineAddsTaskSuccessfully() throws ByteBiteException {
        // Arrange
        String details = "Submit report /by 2024-12-31";
        DeadlineCommand command = new DeadlineCommand(details);
        when(taskList.size()).thenReturn(1);

        // Act
        command.execute(taskList, ui);

        // Assert
        verify(taskList).addTask(any(Task.class));
        verify(ui).showTaskAdded(any(Task.class), eq(1));
    }

    @Test
    void executeEmptyDescriptionThrowsException() {
        // Arrange
        DeadlineCommand command = new DeadlineCommand("");

        // Act & Assert
        assertThrows(EmptyDescriptionException.class, () ->
            command.execute(taskList, ui));
    }

    @Test
    void executeMissingByDelimiterThrowsException() {
        // Arrange
        DeadlineCommand command = new DeadlineCommand("Submit report");

        // Act & Assert
        assertThrows(InvalidFormatException.class, () ->
            command.execute(taskList, ui));
    }

    @Test
    void executeInvalidDateFormatThrowsException() {
        // Arrange
        DeadlineCommand command = new DeadlineCommand("Submit report /by invalid-date");

        // Act & Assert
        assertThrows(InvalidFormatException.class, () ->
            command.execute(taskList, ui));
    }

    @Test
    void executeEmptyDescriptionWithByDelimiterThrowsException() {
        // Arrange
        DeadlineCommand command = new DeadlineCommand(" /by 2024-12-31");

        // Act & Assert
        assertThrows(InvalidFormatException.class, () ->
            command.execute(taskList, ui));
    }

    @Test
    void requiresSaveReturnsTrue() {
        // Arrange
        DeadlineCommand command = new DeadlineCommand("Submit report /by 2024-12-31");

        // Act & Assert
        assertTrue(command.requiresSave());
    }
}
