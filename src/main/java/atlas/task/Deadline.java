package atlas.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {

    private LocalDate by;

    /**
     * Constructs a Deadline task.
     *
     * @param taskName Description of the task
     * @param by Deadline date
     */
    public Deadline(String taskName, LocalDate by) {
        super(taskName);
        this.by = by;
    }

    /**
     * Returns the deadline date.
     *
     * @return Deadline date
     */
    public LocalDate getBy() {
        return by;
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone() ? "1" : "0")
                + " | " + getTaskName()
                + " | " + by;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }
}
