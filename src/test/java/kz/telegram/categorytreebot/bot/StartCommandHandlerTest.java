package kz.telegram.categorytreebot.bot;


import org.junit.jupiter.api.Test;

import static kz.telegram.categorytreebot.bot.StartCommandHandler.START_MSG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartCommandHandlerTest {

    private final StartCommandHandler commandHandler = new StartCommandHandler();

    @Test
    void handle_expectOk() {
        String handledText = commandHandler.handle("/start", 1L);

        assertEquals(START_MSG, handledText);
    }

    @Test
    void shouldHandle() {
        assertTrue(commandHandler.shouldHandle("/start"));
        assertFalse(commandHandler.shouldHandle("/randomCommand"));
    }
}
