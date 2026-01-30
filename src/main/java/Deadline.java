import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate by;

    public Deadline(String taskName, LocalDate by) {
        super(taskName);
        this.by = by;
    }
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
