import java.util.Scanner;

public class Ui {

    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Atlas");
        System.out.println("What can I do for you?");
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine(String message) {
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }
}
