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
                    ui.showGoodbye();
                    return ui.getOutput();

                case "list":
                    ui.showLine("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        ui.showLine((i + 1) + "." + tasks.get(i));
                    }
                    return ui.getOutput();

                case "mark": {
                    int index = Parser.parseMarkIndex(input);
                    tasks.mark(index);
                    Storage.save(tasks.toList());
                    ui.showLine("Nice! I've marked this task as done:");
                    ui.showLine("  " + tasks.get(index));
                    return ui.getOutput();
                }

                case "unmark": {
                    int index = Parser.parseUnmarkIndex(input);
                    tasks.unmark(index);
                    Storage.save(tasks.toList());
                    ui.showLine("OK, I've marked this task as not done yet:");
                    ui.showLine("  " + tasks.get(index));
                    return ui.getOutput();
                }

                case "todo": {
                    String taskName = Parser.parseTodo(input);
                    tasks.add(new Todo(taskName));
                    Storage.save(tasks.toList());
                    ui.showLine("Got it. I've added this task:");
                    ui.showLine("  " + tasks.get(tasks.size() - 1));
                    ui.showLine("Now you have " + tasks.size() + " tasks in the list.");
                    return ui.getOutput();
                }

                case "deadline": {
                    Object[] parsed = Parser.parseDeadline(input);
                    tasks.add(new Deadline((String) parsed[0], (LocalDate) parsed[1]));
                    Storage.save(tasks.toList());
                    ui.showLine("Got it. I've added this task:");
                    ui.showLine("  " + tasks.get(tasks.size() - 1));
                    ui.showLine("Now you have " + tasks.size() + " tasks in the list.");
                    return ui.getOutput();
                }

                case "event": {
                    Object[] parsed = Parser.parseEvent(input);
                    tasks.add(new Event(
                            (String) parsed[0],
                            (LocalDate) parsed[1],
                            (LocalDate) parsed[2]
                    ));
                    Storage.save(tasks.toList());
                    ui.showLine("Got it. I've added this task:");
                    ui.showLine("  " + tasks.get(tasks.size() - 1));
                    ui.showLine("Now you have " + tasks.size() + " tasks in the list.");
                    return ui.getOutput();
                }

                case "delete": {
                    int index = Parser.parseDeleteIndex(input);
                    ui.showLine("Noted. I've removed this task:");
                    ui.showLine("  " + tasks.get(index));
                    tasks.delete(index);
                    Storage.save(tasks.toList());
                    ui.showLine("Now you have " + tasks.size() + " tasks in the list.");
                    return ui.getOutput();
                }

                case "find": {
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

                default:
                    throw new AtlasException("No such command exists");
            }

        } catch (AtlasException e) {
            ui.showError(e.getMessage());
            return ui.getOutput();
        }
    }
}
