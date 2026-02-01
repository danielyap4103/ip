package atlas.task;

/**
 * Represents a simple to-do task without a date.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task.
     *
     * @param taskName Description of the todo
     */
    public Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone() ? "1" : "0") + " | " + getTaskName();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
