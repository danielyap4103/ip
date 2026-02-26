package atlas.parser;

import atlas.AtlasException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


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
        assert parts.length >= 2
                : "Mark command missing index";
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
        assert parts.length >= 2
                : "Unmark command missing index";
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
        assert parts.length >= 2
                : "Delete command missing index";
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
    public static Object[] parseDeadline(String input) throws AtlasException {

        if (input.length() <= 9) {
            throw new AtlasException("The description of a deadline cannot be empty.");
        }

        String body = input.substring(9).trim();

        if (!body.contains(" /by ")) {
            throw new AtlasException("Deadline must be in format: deadline DESCRIPTION /by YYYY-MM-DD");
        }

        String[] parts = body.split(" /by ", 2);

        String description = parts[0].trim();
        String dateString = parts[1].trim();

        if (description.isEmpty()) {
            throw new AtlasException("The description of a deadline cannot be empty.");
        }

        try {
            LocalDate date = LocalDate.parse(dateString);
            return new Object[]{description, date};
        } catch (Exception e) {
            throw new AtlasException("Date must be in format YYYY-MM-DD.");
        }
    }


    /**
     * Parses an event command.
     *
     * @param input User input
     * @return Parsed description, start date, and end date
     * @throws AtlasException If format is invalid
     */
    public static Object[] parseEvent(String input) throws AtlasException {

        if (input.length() <= 6) {
            throw new AtlasException("The description of an event cannot be empty.");
        }

        String body = input.substring(6).trim();

        if (!body.contains(" /from ") || !body.contains(" /to ")) {
            throw new AtlasException(
                    "Event must be in format: event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD"
            );
        }

        try {
            String[] firstSplit = body.split(" /from ", 2);
            String description = firstSplit[0].trim();

            String[] secondSplit = firstSplit[1].split(" /to ", 2);

            LocalDate from = LocalDate.parse(secondSplit[0].trim());
            LocalDate to = LocalDate.parse(secondSplit[1].trim());

            return new Object[]{description, from, to};

        } catch (Exception e) {
            throw new AtlasException("Dates must be in format YYYY-MM-DD.");
        }
    }


    public static Object[] parseUpdate(String input)
            throws AtlasException {

        String[] parts = input.split(" ", 3);

        if (parts.length < 3) {
            throw new AtlasException(
                    "Usage: update INDEX NEW_DETAILS"
            );
        }

        int index;

        try {
            index = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new AtlasException("Invalid task index.");
        }

        String remainder = parts[2].trim();

        if (remainder.isEmpty()) {
            throw new AtlasException("Updated content cannot be empty.");
        }

        return new Object[]{index, remainder};
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
