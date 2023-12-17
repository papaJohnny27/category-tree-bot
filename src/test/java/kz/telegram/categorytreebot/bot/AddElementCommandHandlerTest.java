package kz.telegram.categorytreebot.bot;

import kz.telegram.categorytreebot.exception.CategoryAlreadyExistsException;
import kz.telegram.categorytreebot.exception.CategoryNotFoundException;
import kz.telegram.categorytreebot.service.CategoryService;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddElementCommandHandlerTest {

    private final CategoryService categoryService = Mockito.mock(CategoryService.class);
    private final AddElementCommandHandler commandHandler =
            new AddElementCommandHandler(categoryService);

    @Test
    void handle_whenCategoryAlreadyExistsException_thenReturnErrorMsg() {
        Long userId = 1L;
        String category = randomAlphabetic(10);
        Mockito.doThrow(new CategoryAlreadyExistsException("msg"))
                .when(categoryService).addCategory(category, "", userId);

        String handledText = commandHandler.handle("/addElement " + category, userId);

        assertEquals(String.format("Категория %s уже существует", category), handledText);
    }

    @Test
    void handle_whenCategoryNotFoundException_thenReturnErrorMsg() {
        Long userId = 1L;
        String parent = randomAlphabetic(10);
        String category = randomAlphabetic(10);
        Mockito.doThrow(new CategoryNotFoundException("msg"))
                .when(categoryService).addCategory(category, parent, userId);

        String handledText = commandHandler.handle("/addElement " + " " + parent + " "  + category, userId);

        assertEquals("Не найдена родительская категория: " + parent, handledText);
    }

    @Test
    void handle_whenUnexpectedException_thenReturnErrorMsg() {
        Long userId = 1L;
        String category = randomAlphabetic(10);
        Mockito.doThrow(new LazyInitializationException("msg"))
                .when(categoryService).addCategory(category, "", userId);

        String handledText = commandHandler.handle("/addElement " + category, userId);

        assertEquals("Непредвиденная ошибка при добавлении категории", handledText);
    }

    @Test
    void handle_expectOk() {
        Long userId = 1L;
        String category = randomAlphabetic(10);
        Mockito.doNothing().when(categoryService)
                .addCategory(category, "", userId);

        String handledText = commandHandler.handle("/addElement " + category, userId);

        assertEquals("Категория успешно добавлена", handledText);
    }

    @Test
    void shouldHandle() {
        assertTrue(commandHandler.shouldHandle("/addElement"));
        assertFalse(commandHandler.shouldHandle("/randomCommand"));
    }
}
