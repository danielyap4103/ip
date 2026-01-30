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

                if (input.equals("bye")) {
                    ui.showGoodbye();
                    break;

                } else if (input.equals("list")) {
                    ui.showLine("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        ui.showLine((i + 1) + "." + tasks.get(i));
                    }

                } else if (input.startsWith("mark")) {
                    if (input.length() <= 4) {
                        throw new AtlasException("mark has to be used with the task index following it");
                    }
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    tasks.mark(index);
                    Storage.save(tasks.toList());

                    ui.showLine("Nice! I've marked this task as done:");
                    ui.showLine("  " + tasks.get(index));

                } else if (input.startsWith("unmark")) {
                    if (input.length() <= 6) {
                        throw new AtlasException("unmark has to be used with the task index following it");
                    }
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    tasks.unmark(index);
                    Storage.save(tasks.toList());

                    ui.showLine("OK, I've marked this task as not done yet:");
                    ui.showLine("  " + tasks.get(index));

                } else if (input.startsWith("todo")) {
                    if (input.length() <= 4) {
                        throw new AtlasException("The description of a todo cannot be empty.");
                    }
                    String taskName = input.substring(5);
                    tasks.add(new Todo(taskName));
                    Storage.save(tasks.toList());

                    ui.showLine("Got it. I've added this task:");
                    ui.showLine("  " + tasks.get(tasks.size() - 1));
                    ui.showLine("Now you have " + tasks.size() + " tasks in the list.");

                } else if (input.startsWith("deadline")) {
                    String[] parts = input.substring(9).split(" /by ");
                    if (parts.length < 2) {
                        throw new AtlasException("Deadline must have a /by date.");
                    }

                    LocalDate by = LocalDate.parse(parts[1]);
                    tasks.add(new Deadline(parts[0], by));
                    Storage.save(tasks.toList());

                    ui.showLine("Got it. I've added this task:");
                    ui.showLine("  " + tasks.get(tasks.size() - 1));
                    ui.showLine("Now you have " + tasks.size() + " tasks in the list.");

                } else if (input.startsWith("event")) {
                    String[] parts = input.substring(6).split(" /from | /to ");
                    if (parts.length < 3) {
                        throw new AtlasException("Event must have /from and /to.");
                    }

                    LocalDate from = LocalDate.parse(parts[1]);
                    LocalDate to = LocalDate.parse(parts[2]);
                    tasks.add(new Event(parts[0], from, to));
                    Storage.save(tasks.toList());

                    ui.showLine("Got it. I've added this task:");
                    ui.showLine("  " + tasks.get(tasks.size() - 1));
                    ui.showLine("Now you have " + tasks.size() + " tasks in the list.");

                } else if (input.startsWith("delete")) {
                    if (input.length() <= 6) {
                        throw new AtlasException("delete has to be used with the task index following it");
                    }

                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new AtlasException("Task index is out of range.");
                    }

                    ui.showLine("Noted. I've removed this task:");
                    ui.showLine("  " + tasks.get(index));
                    tasks.delete(index);
                    Storage.save(tasks.toList());

                    ui.showLine("Now you have " + tasks.size() + " tasks in the list.");

                } else {
                    throw new AtlasException("No such command exists");
                }

            } catch (AtlasException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.close();
    }

    public static void main(String[] args) {
        new Atlas().run();
    }
}
