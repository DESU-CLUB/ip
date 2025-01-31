package taskmanager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.task.Task;  // Added this import
import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListCommandTest {
    private TaskList taskList;
    private Ui ui;
    private ListCommand command;

    @BeforeEach
    void setUp() {
        taskList = mock(TaskList.class);
        ui = mock(Ui.class);
        command = new ListCommand();
    }

    @Test
    void executeShowsTaskList() throws ByteBiteException {
        // Arrange
        ArrayList<Task> mockTasks = new ArrayList<>();
        when(taskList.getTaskList()).thenReturn(mockTasks);

        // Act
        command.execute(taskList, ui);

        // Assert
        verify(ui).showTaskList(mockTasks);
    }

    @Test
    void requiresSaveReturnsFalse() {
        // Act & Assert
        assertFalse(command.requiresSave());
    }
}
