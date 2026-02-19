package atlas;

import atlas.parser.Parser;
import atlas.storage.Storage;
import atlas.task.Deadline;
import atlas.task.Event;
import atlas.task.Task;
import atlas.task.TaskList;
import atlas.task.Todo;
import atlas.ui.Ui;

import java.time.LocalDate;
import java.util.List;

public class Atlas {

    private Ui ui;
    private TaskList tasks;

    public Atlas(Ui ui) {
        this.ui = ui;
        this.tasks = new TaskList(Storage.load());
        ui.showWelcome();
    }

    public String getResponse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ui.getOutput();
        }

        try {
            String command = Parser.getCommandWord(input);

            switch (command) {
            case "bye":
                return handleBye();

            case "list":
                return handleList();

            case "mark":
                return handleMark(input);

            case "unmark":
                return handleUnmark(input);

            case "todo":
                return handleTodo(input);

            case "deadline":
                return handleDeadline(input);

            case "event":
                return handleEvent(input);

            case "delete":
                return handleDelete(input);

            case "find":
                return handleFind(input);

            case "update":
                return handleUpdate(input);


            default:
                throw new AtlasException("No such command exists");
            }

        } catch (AtlasException e) {
            ui.showError(e.getMessage());
            return ui.getOutput();
        }
    }

    private String handleBye() {
        ui.showGoodbye();
        return ui.getOutput();
    }

    private String handleList() {
        ui.showLine("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            ui.showLine((i + 1) + "." + tasks.get(i));
        }
        return ui.getOutput();
    }

    private String handleMark(String input) throws AtlasException {
        int index = Parser.parseMarkIndex(input);
        validateIndex(index);

        tasks.mark(index);
        Storage.save(tasks.toList());

        ui.showLine("Nice! I've marked this task as done:");
        ui.showLine("  " + tasks.get(index));
        return ui.getOutput();
    }

    private String handleUnmark(String input) throws AtlasException {
        int index = Parser.parseUnmarkIndex(input);
        validateIndex(index);

        tasks.unmark(index);
        Storage.save(tasks.toList());

        ui.showLine("OK, I've marked this task as not done yet:");
        ui.showLine("  " + tasks.get(index));
        return ui.getOutput();
    }

    private String handleTodo(String input) throws AtlasException {
        String taskName = Parser.parseTodo(input);
        tasks.add(new Todo(taskName));
        Storage.save(tasks.toList());

        showTaskAdded();
        return ui.getOutput();
    }

    private String handleDeadline(String input) throws AtlasException {
        Object[] parsed = Parser.parseDeadline(input);
        tasks.add(new Deadline((String) parsed[0], (LocalDate) parsed[1]));
        Storage.save(tasks.toList());

        showTaskAdded();
        return ui.getOutput();
    }

    private String handleEvent(String input) throws AtlasException {
        Object[] parsed = Parser.parseEvent(input);
        tasks.add(new Event(
                (String) parsed[0],
                (LocalDate) parsed[1],
                (LocalDate) parsed[2]
        ));
        Storage.save(tasks.toList());

        showTaskAdded();
        return ui.getOutput();
    }

    private String handleDelete(String input) throws AtlasException {
        int index = Parser.parseDeleteIndex(input);
        validateIndex(index);

        Task removed = tasks.delete(index);
        Storage.save(tasks.toList());

        ui.showLine("Noted. I've removed this task:");
        ui.showLine("  " + removed);
        ui.showLine("Now you have " + tasks.size() + " tasks in the list.");
        return ui.getOutput();
    }

    private String handleFind(String input) throws AtlasException {
        String keyword = Parser.parseFindKeyword(input);
        List<Task> matches = tasks.findByKeyword(keyword);

        ui.showLine("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (matches.contains(task)) {
                ui.showLine((i + 1) + "." + task);
            }
        }
        return ui.getOutput();
    }

    private String handleUpdate(String input) throws AtlasException {
        Object[] parsed = Parser.parseUpdate(input);
        int index = (int) parsed[0];
        String updatePart = (String) parsed[1];

        validateIndex(index);

        Task task = tasks.get(index);

        if (updatePart.startsWith("/desc ")) {
            String newDesc = updatePart.substring(6);
            task.setTaskName(newDesc);
        } else if (task instanceof Deadline && updatePart.startsWith("/by ")) {
            LocalDate newDate = LocalDate.parse(updatePart.substring(4));
            ((Deadline) task).setBy(newDate);
        } else if (task instanceof Event) {
            if (updatePart.startsWith("/from ")) {
                String[] dates = updatePart.split(" ");
                if (dates.length != 4 || !dates[2].equals("/to")) {
                    throw new AtlasException("Usage: update INDEX /from DATE /to DATE");
                }
                LocalDate from = LocalDate.parse(dates[1]);
                LocalDate to = LocalDate.parse(dates[3]);
                ((Event) task).setFrom(from);
                ((Event) task).setTo(to);
            } else {
                throw new AtlasException("Invalid update format for Event.");
            }
        } else {
            throw new AtlasException("Invalid update format.");
        }

        Storage.save(tasks.toList());

        ui.showLine("Updated task:");
        ui.showLine("  " + task);

        return ui.getOutput();
    }

    private void validateIndex(int index) throws AtlasException {
        if (index < 0 || index >= tasks.size()) {
            throw new AtlasException("Task index is out of range.");
        }
    }

    private void showTaskAdded() {
        ui.showLine("Got it. I've added this task:");
        ui.showLine("  " + tasks.get(tasks.size() - 1));
        ui.showLine("Now you have " + tasks.size() + " tasks in the list.");
    }
}
