public class Deadline extends Task {
    private String by;

    public Deadline(String taskName, String by) {
        super(taskName);
        this.by = by;
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone() ? "1" : "0")
                + " | " + getTaskName()
                + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
