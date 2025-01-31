// EmptyDescriptionException.java
package taskmanager.utils;

public class EmptyDescriptionException extends ByteBiteException {
    public EmptyDescriptionException(String taskType) {
        super("The description of a " + taskType + " cannot be empty!");
    }
}
