package atlas.task;

/**
 * Represents a generic task in Atlas.
 * This class is intended to be subclassed by specific task types.
 */
public abstract class Task {

    private String taskName;
    private boolean isDone;

    /**
     * Constructs a task with the given name.
     *
     * @param taskName Description of the task
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /**
     * Marks the task as completed.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return True if completed, false otherwise
     */
    protected boolean isDone() {
        return isDone;
    }

    /**
     * Returns the task description.
     *
     * @return Task name
     */
    protected String getTaskName() {
        return taskName;
    }

    /**
     * Returns the status icon for display.
     *
     * @return "X" if done, otherwise a space
     */
    protected String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Converts the task into a string format suitable for file storage.
     *
     * @return File-compatible string representation
     */
    public abstract String toFileString();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + taskName;
    }
}
