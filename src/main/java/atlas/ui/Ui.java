package atlas.ui;

public class Ui {

    private StringBuilder output;

    public Ui() {
        output = new StringBuilder();
    }

    public void showWelcome() {
        output.append("Hello! I'm Atlas\n");
        output.append("What can I do for you?\n");
    }

    public void showGoodbye() {
        output.append("Bye. Hope to see you again soon!\n");
    }

    public void showError(String message) {
        output.append("OOPS!!! ").append(message).append("\n");
    }

    public void showLine(String message) {
        output.append(message).append("\n");
    }

    public String getOutput() {
        String result = output.toString();
        output.setLength(0);
        return result;
    }
}
