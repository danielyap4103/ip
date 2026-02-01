package atlas.task;

public abstract class Task {

    private String taskName;
    private boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    protected boolean isDone() {
        return isDone;
    }

    protected String getTaskName() {
        return taskName;
    }

    protected String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public abstract String toFileString();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + taskName;
    }
}
