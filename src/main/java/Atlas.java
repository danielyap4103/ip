import java.util.Scanner;
import java.util.List;
import java.time.LocalDate;


public class Atlas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = Storage.load();

        System.out.println("Hello! I'm Atlas");
        System.out.println("What can I do for you?");

        while (true) {
            try {
                String input = scanner.nextLine();

                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                } else if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                } else if (input.startsWith("mark")) {
                    if (input.length() <= 4) {
                        throw new AtlasException("mark has to be used with the task index following it");
                    }
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    tasks.get(index).markDone();
                    Storage.save(tasks);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(index));
                } else if (input.startsWith("unmark")) {
                    if (input.length() <= 6) {
                        throw new AtlasException("unmark has to be used with the task index following it");
                    }
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    tasks.get(index).unmark();
                    Storage.save(tasks);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(index));
                } else if (input.startsWith("todo")) {
                    if (input.length() <= 4) {
                        throw new AtlasException("The description of a todo cannot be empty.");
                    }
                    String taskName = input.substring(5);
                    tasks.add(new Todo(taskName));
                    Storage.save(tasks);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else if (input.startsWith("deadline")) {
                    String[] parts = input.substring(9).split(" /by ");
                    if (parts.length < 2) {
                        throw new AtlasException("Deadline must have a /by date.");
                    }
                    LocalDate by = LocalDate.parse(parts[1]);
                    tasks.add(new Deadline(parts[0], by));
                    Storage.save(tasks);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else if (input.startsWith("event")) {
                    String[] parts = input.substring(6).split(" /from | /to ");
                    if (parts.length < 3) {
                        throw new AtlasException("Event must have /from and /to.");
                    }
                    LocalDate from = LocalDate.parse(parts[1]);
                    LocalDate to = LocalDate.parse(parts[2]);
                    tasks.add(new Event(parts[0], from, to));

                    Storage.save(tasks);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else if (input.startsWith("delete")) {
                    if (input.length() <= 6) {
                        throw new AtlasException("delete has to be used with the task index following it");
                    }

                    int index = Integer.parseInt(input.substring(7)) - 1;

                    if (index < 0 || index >= tasks.size()) {
                        throw new AtlasException("Task index is out of range.");
                    }

                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + tasks.get(index));
                    tasks.remove(index);
                    Storage.save(tasks);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    throw new AtlasException("No such command exists");
                }

            } catch (AtlasException e) {
                System.out.println("OOPS!!! " + e.getMessage());
            }
        }


        scanner.close();
    }
}
