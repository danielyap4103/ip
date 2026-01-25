import java.util.Scanner;

public class Atlas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int count = 0;

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
                    for (int i = 0; i < count; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                } else if (input.startsWith("mark")) {
                    if (input.length() <= 4) {
                        throw new AtlasException("mark has to be used with the task index following it");
                    }
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    tasks[index].markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[index]);
                } else if (input.startsWith("unmark")) {
                    if (input.length() <= 6) {
                        throw new AtlasException("unmark has to be used with the task index following it");
                    }
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    tasks[index].unmark();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[index]);
                } else if (input.startsWith("todo")) {
                    if (input.length() <= 4) {
                        throw new AtlasException("The description of a todo cannot be empty.");
                    }
                    String taskName = input.substring(5);
                    tasks[count++] = new Todo(taskName);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[count - 1]);
                    System.out.println("Now you have " + count + " tasks in the list.");
                } else if (input.startsWith("deadline")) {
                    String[] parts = input.substring(9).split(" /by ");
                    if (parts.length < 2) {
                        throw new AtlasException("Deadline must have a /by date.");
                    }
                    tasks[count++] = new Deadline(parts[0], parts[1]);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[count - 1]);
                    System.out.println("Now you have " + count + " tasks in the list.");
                } else if (input.startsWith("event")) {
                    String[] parts = input.substring(6).split(" /from | /to ");
                    if (parts.length < 3) {
                        throw new AtlasException("Event must have /from and /to.");
                    }
                    tasks[count++] = new Event(parts[0], parts[1], parts[2]);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[count - 1]);
                    System.out.println("Now you have " + count + " tasks in the list.");
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
