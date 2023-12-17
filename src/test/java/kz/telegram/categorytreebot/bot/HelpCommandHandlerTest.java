package kz.telegram.categorytreebot.bot;

import org.junit.jupiter.api.Test;

import static kz.telegram.categorytreebot.bot.HelpCommandHandler.HELP_MSG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelpCommandHandlerTest {

    private final HelpCommandHandler commandHandler = new HelpCommandHandler();

    @Test
    void handle_expectOk() {
        String handledText = commandHandler.handle("/help", 1L);

        assertEquals(HELP_MSG, handledText);
    }

    @Test
    void shouldHandle() {
        assertTrue(commandHandler.shouldHandle("/help"));
        assertFalse(commandHandler.shouldHandle("/randomCommand"));
    }
}
