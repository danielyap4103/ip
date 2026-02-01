package atlas.parser;

import atlas.AtlasException;
import java.time.LocalDate;

/**
 * Parses user input into commands and arguments.
 */
public class Parser {

    /**
     * Extracts the command word from user input.
     *
     * @param input Raw user input
     * @return Command word
     */
    public static String getCommandWord(String input) {
        if (input == null) {
            return "";
        }

        input = input.trim();

        if (input.isEmpty()) {
            return "";
        }

        String[] parts = input.split("\\s+");
        return parts[0];
    }

    /**
     * Parses the index for mark command.
     *
     * @param input User input
     * @return Task index
     * @throws AtlasException If index is invalid
     */
    public static int parseMarkIndex(String input)
            throws AtlasException {

        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new AtlasException(
                    "mark has to be used with the task index"
            );
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new AtlasException("Invalid task index.");
        }
    }

    /**
     * Parses the index for unmark command.
     *
     * @param input User input
     * @return Task index
     * @throws AtlasException If index is invalid
     */
    public static int parseUnmarkIndex(String input)
            throws AtlasException {

        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new AtlasException(
                    "unmark has to be used with the task index"
            );
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new AtlasException("Invalid task index.");
        }
    }

    /**
     * Parses the index for delete command.
     *
     * @param input User input
     * @return Task index
     * @throws AtlasException If index is invalid
     */
    public static int parseDeleteIndex(String input)
            throws AtlasException {

        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new AtlasException(
                    "delete has to be used with the task index"
            );
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new AtlasException("Invalid task index.");
        }
    }

    /**
     * Parses a todo description.
     *
     * @param input User input
     * @return Todo description
     * @throws AtlasException If description is missing
     */
    public static String parseTodo(String input)
            throws AtlasException {

        if (input.length() <= 4) {
            throw new AtlasException(
                    "The description of a todo cannot be empty."
            );
        }
        return input.substring(5);
    }

    /**
     * Parses a deadline command.
     *
     * @param input User input
     * @return Parsed description and date
     * @throws AtlasException If format is invalid
     */
    public static Object[] parseDeadline(String input)
            throws AtlasException {

        String[] parts = input.substring(9).split(" /by ");
        if (parts.length < 2) {
            throw new AtlasException(
                    "Deadline must have a /by date."
            );
        }

        return new Object[] {
                parts[0],
                LocalDate.parse(parts[1])
        };
    }

    /**
     * Parses an event command.
     *
     * @param input User input
     * @return Parsed description, start date, and end date
     * @throws AtlasException If format is invalid
     */
    public static Object[] parseEvent(String input)
            throws AtlasException {

        String[] parts =
                input.substring(6).split(" /from | /to ");
        if (parts.length < 3) {
            throw new AtlasException(
                    "Event must have /from and /to."
            );
        }

        return new Object[] {
                parts[0],
                LocalDate.parse(parts[1]),
                LocalDate.parse(parts[2])
        };
    }

    /**
     * Parses the keyword for find command.
     *
     * @param input User input
     * @return Keyword to search for
     * @throws AtlasException If keyword is missing
     */
    public static String parseFindKeyword(String input)
            throws AtlasException {

        if (input.length() <= 4) {
            throw new AtlasException(
                    "The keyword to find cannot be empty."
            );
        }
        return input.substring(5);
    }
}
