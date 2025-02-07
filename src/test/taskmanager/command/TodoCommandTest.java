package taskmanager.command;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import taskmanager.task.Task;
import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.EmptyDescriptionException;

class TodoCommandTest {
    private TaskList taskList;
    private Ui ui;

    @BeforeEach
    void setUp() {
        taskList = mock(TaskList.class);
        ui = mock(Ui.class);
    }

    @Test
    void executeValidTaskAddsTaskSuccessfully() throws ByteBiteException {
        // Arrange
        String details = "Read a book";
        TodoCommand command = new TodoCommand(details);
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
        TodoCommand command = new TodoCommand("");

        // Act & Assert
        assertThrows(EmptyDescriptionException.class, () ->
            command.execute(taskList, ui));
    }

    @Test
    void requiresSaveReturnsTrue() {
        // Arrange
        TodoCommand command = new TodoCommand("Read a book");

        // Act & Assert
        assertTrue(command.requiresSave());
    }
}
