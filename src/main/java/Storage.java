import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static final String DATA_DIR = "data";
    private static final String FILE_PATH = "data/atlas.txt";

    // Load tasks from storage to atlas
    public static List<Task> load() {
        ensureFileExists();
        List<Task> tasks = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(FILE_PATH));
            while (scanner.hasNextLine()) {
                tasks.add(parseTask(scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // first run file does not exist yet
        }

        return tasks;
    }

    // Save tasks from atlas to storage after any modification
    public static void save(List<Task> tasks) {
        ensureFileExists();
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    private static void ensureFileExists() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating data file.");
        }
    }

    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");

        Task task;
        if (type.equals("T")) {
            task = new Todo(parts[2]);
        } else if (type.equals("D")) {
            task = new Deadline(parts[2], parts[3]);
        } else if (type.equals("E")) {
            task = new Event(parts[2], parts[3], parts[4]);
        } else {
            throw new IllegalArgumentException("Corrupted data file");
        }

        if (isDone) {
            task.markDone();
        }

        return task;
    }
}
