package atlas;

import atlas.parser.Parser;
import atlas.storage.Storage;
import atlas.task.Deadline;
import atlas.task.Event;
import atlas.task.TaskList;
import atlas.task.Todo;
import atlas.ui.Ui;

import java.time.LocalDate;

public class Atlas {

    private Ui ui;
    private TaskList tasks;

    public Atlas() {
        ui = new Ui();
        tasks = new TaskList(Storage.load());
    }

    public void run() {
        ui.showWelcome();

        while (true) {
            try {
                String input = ui.readCommand();
                String command = Parser.getCommandWord(input);

                switch (command) {
                    case "bye":
                        ui.showGoodbye();
                        ui.close();
                        return;

                    case "list":
                        ui.showLine("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            ui.showLine((i + 1) + "." + tasks.get(i));
                        }
                        break;

                    case "mark": {
                        int index = Parser.parseMarkIndex(input);
                        tasks.mark(index);
                        Storage.save(tasks.toList());

                        ui.showLine("Nice! I've marked this task as done:");
                        ui.showLine("  " + tasks.get(index));
                        break;
                    }

                    case "unmark": {
                        int index = Parser.parseUnmarkIndex(input);
                        tasks.unmark(index);
                        Storage.save(tasks.toList());

                        ui.showLine("OK, I've marked this task as not done yet:");
                        ui.showLine("  " + tasks.get(index));
                        break;
                    }

                    case "todo": {
                        String taskName = Parser.parseTodo(input);
                        tasks.add(new Todo(taskName));
                        Storage.save(tasks.toList());

                        ui.showLine("Got it. I've added this task:");
                        ui.showLine("  " + tasks.get(tasks.size() - 1));
                        ui.showLine(
                                "Now you have " + tasks.size()
                                        + " tasks in the list."
                        );
                        break;
                    }

                    case "deadline": {
                        Object[] parsed = Parser.parseDeadline(input);
                        tasks.add(
                                new Deadline(
                                        (String) parsed[0],
                                        (LocalDate) parsed[1]
                                )
                        );
                        Storage.save(tasks.toList());

                        ui.showLine("Got it. I've added this task:");
                        ui.showLine("  " + tasks.get(tasks.size() - 1));
                        ui.showLine(
                                "Now you have " + tasks.size()
                                        + " tasks in the list."
                        );
                        break;
                    }

                    case "event": {
                        Object[] parsed = Parser.parseEvent(input);
                        tasks.add(
                                new Event(
                                        (String) parsed[0],
                                        (LocalDate) parsed[1],
                                        (LocalDate) parsed[2]
                                )
                        );
                        Storage.save(tasks.toList());

                        ui.showLine("Got it. I've added this task:");
                        ui.showLine("  " + tasks.get(tasks.size() - 1));
                        ui.showLine(
                                "Now you have " + tasks.size()
                                        + " tasks in the list."
                        );
                        break;
                    }

                    case "delete": {
                        int index = Parser.parseDeleteIndex(input);
                        if (index < 0 || index >= tasks.size()) {
                            throw new AtlasException(
                                    "task index is out of range."
                            );
                        }

                        ui.showLine("Noted. I've removed this task:");
                        ui.showLine("  " + tasks.get(index));
                        tasks.delete(index);
                        Storage.save(tasks.toList());

                        ui.showLine(
                                "Now you have " + tasks.size()
                                        + " tasks in the list."
                        );
                        break;
                    }

                    default:
                        throw new AtlasException("No such command exists");
                }

            } catch (AtlasException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Atlas().run();
    }
}
