package atlas.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void getCommandWord_normalInput_correctCommandReturned() {
        String input = "mark 2";

        String command = Parser.getCommandWord(input);

        assertEquals("mark", command);
    }

    @Test
    public void getCommandWord_inputWithExtraSpaces_commandStillCorrect() {
        String input = "   delete   3  ";

        String command = Parser.getCommandWord(input);

        assertEquals("delete", command);
    }
}
