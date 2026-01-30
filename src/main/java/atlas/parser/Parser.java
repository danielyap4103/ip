package atlas.parser;

import atlas.AtlasException;

import java.time.LocalDate;

public class Parser {

    public static String getCommandWord(String input) {
        return input.split(" ")[0];
    }

    public static int parseMarkIndex(String input) throws AtlasException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new AtlasException("mark has to be used with the atlas.task index following it");
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new AtlasException("Invalid atlas.task index.");
        }
    }

    public static int parseUnmarkIndex(String input) throws AtlasException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new AtlasException("unmark has to be used with the atlas.task index following it");
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new AtlasException("Invalid atlas.task index.");
        }
    }

    public static int parseDeleteIndex(String input) throws AtlasException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new AtlasException("delete has to be used with the atlas.task index following it");
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new AtlasException("Invalid atlas.task index.");
        }
    }

    public static String parseTodo(String input) throws AtlasException {
        if (input.length() <= 4) {
            throw new AtlasException("The description of a todo cannot be empty.");
        }
        return input.substring(5);
    }

    public static Object[] parseDeadline(String input) throws AtlasException {
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length < 2) {
            throw new AtlasException("atlas.task.Deadline must have a /by date.");
        }

        return new Object[] {
                parts[0],
                LocalDate.parse(parts[1])
        };
    }

    public static Object[] parseEvent(String input) throws AtlasException {
        String[] parts = input.substring(6).split(" /from | /to ");
        if (parts.length < 3) {
            throw new AtlasException("atlas.task.Event must have /from and /to.");
        }

        return new Object[] {
                parts[0],
                LocalDate.parse(parts[1]),
                LocalDate.parse(parts[2])
        };
    }
}
