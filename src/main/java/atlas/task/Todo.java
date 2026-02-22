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
    // AI-assisted improvement:
    // Added assertion to ensure todo description is not null or empty.
    // ChatGPT suggested strengthening constructor validation.

    public Todo(String taskName) {
        super(taskName);
        assert taskName != null && !taskName.trim().isEmpty()
                : "Todo description should not be empty";
    }


    @Override
    public String toFileString() {
        return "T | " + (isDone() ? "1" : "0")
                + " | " + getTaskName();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
