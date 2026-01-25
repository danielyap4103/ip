import java.util.Scanner;

public class Atlas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int count = 0;

        System.out.println("Hello! I'm Atlas");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < count; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].markDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[index]);
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].unmark();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[index]);
            } else if (input.startsWith("todo ")) {
                String taskName = input.substring(5);
                tasks[count++] = new Todo(taskName);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count - 1]);
                System.out.println("Now you have " + count + " tasks in the list.");
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ");
                tasks[count++] = new Deadline(parts[0], parts[1]);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count - 1]);
                System.out.println("Now you have " + count + " tasks in the list.");
            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from | /to ");
                tasks[count++] = new Event(parts[0], parts[1], parts[2]);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count - 1]);
                System.out.println("Now you have " + count + " tasks in the list.");
            }
        }

        scanner.close();
    }
}
