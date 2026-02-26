package atlas.storage;

import atlas.task.Deadline;
import atlas.task.Event;
import atlas.task.Task;
import atlas.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to persistent storage.
 */
public class Storage {

    private static final String DATA_DIR = "data";
    private static final String FILE_PATH = "data/atlas.txt";

    /**
     * Loads tasks from storage.
     *
     * @return List of loaded tasks
     */
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
            // First run: file does not exist
        }

        return tasks;
    }

    /**
     * Saves tasks to storage.
     *
     * @param tasks List of tasks to save
     */
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

    /**
     * Ensures the data directory and file exist.
     */
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

    /**
     * Parses a task from a line in the data file.
     *
     * @param line Raw file line
     * @return Parsed task
     */
    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3
                : "Invalid storage format: insufficient parts";
        String type = parts[0];
        boolean isDone = parts[1].equals("1");

        Task task;
        if (type.equals("T")) {
            task = new Todo(parts[2]);
        } else if (type.equals("D")) {
            task = new Deadline(parts[2], LocalDate.parse(parts[3]));
        } else if (type.equals("E")) {
            task = new Event(
                    parts[2],
                    LocalDate.parse(parts[3]),
                    LocalDate.parse(parts[4])
            );
        } else {
            throw new IllegalArgumentException("Corrupted data file");
        }

        if (isDone) {
            task.markDone();
        }

        return task;
    }
}

