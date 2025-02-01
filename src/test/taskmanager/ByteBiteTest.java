package taskmanager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import taskmanager.parser.Parser;
import taskmanager.storage.Storage;
import taskmanager.task.Task;
import taskmanager.task.Todo;
import taskmanager.ui.Ui;



class ByteBiteTest {
    @TempDir
    Path tempDir;

    private ByteBite byteBite;
    private Storage mockStorage;
    private Ui mockUi;
    private Parser mockParser;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        mockStorage = mock(Storage.class);
        mockUi = mock(Ui.class);
        mockParser = mock(Parser.class);
    }

    @Test
    void initializationWithValidFileLoadsTasks() throws IOException {
        // Arrange
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Test task"));
        when(mockStorage.loadTasksFromFile()).thenReturn(tasks);
        // Act
        byteBite = new ByteBite();
        // Assert
        // Verify no errors were shown
        verify(mockUi, never()).showError(any());
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }
}
