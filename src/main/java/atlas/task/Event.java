package atlas.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start and end date.
 */
public class Event extends Task {

    private LocalDate from;
    private LocalDate to;

    /**
     * Constructs an Event task.
     *
     * @param taskName Description of the event
     * @param from Start date
     * @param to End date
     */
    public Event(String taskName, LocalDate from, LocalDate to) {
        super(taskName);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start date of the event.
     *
     * @return Start date
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns the end date of the event.
     *
     * @return End date
     */
    public LocalDate getTo() {
        return to;
    }

    public void setFrom(LocalDate from) {
        assert from != null : "Event start cannot be null";
        this.from = from;
    }

    public void setTo(LocalDate to) {
        assert to != null : "Event end cannot be null";
        this.to = to;
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone() ? "1" : "0")
                + " | " + getTaskName()
                + " | " + from
                + " | " + to;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[E]" + super.toString()
                + " (from: " + from.format(formatter)
                + " to: " + to.format(formatter) + ")";
    }
}
