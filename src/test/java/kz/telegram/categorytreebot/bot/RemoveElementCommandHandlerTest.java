package kz.telegram.categorytreebot.bot;

import kz.telegram.categorytreebot.exception.CategoryNotFoundException;
import kz.telegram.categorytreebot.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveElementCommandHandlerTest {

    private final CategoryService categoryService = Mockito.mock(CategoryService.class);
    private final RemoveElementCommandHandler commandHandler =
            new RemoveElementCommandHandler(categoryService);

    @Test
    void handle_whenCategoryNotFoundException_thenReturnErrorMessage() {
        Long userId = 1L;
        String category = randomAlphabetic(10);
        Mockito.doThrow(new CategoryNotFoundException("msg"))
                .when(categoryService).deleteCategory(category, userId);

        String handledText = commandHandler.handle("/removeElement " + category, userId);

        assertEquals("Категория не найдена", handledText);
    }

    @Test
    void handle_expectOk() {
        Long userId = 1L;
        String category = randomAlphabetic(10);
        Mockito.doNothing().when(categoryService).deleteCategory(category, userId);

        String handledText = commandHandler.handle("/removeElement " + category, userId);

        assertEquals("Категория успешно удалена", handledText);
    }

    @Test
    void shouldHandle() {
        assertTrue(commandHandler.shouldHandle("/removeElement"));
        assertFalse(commandHandler.shouldHandle("/randomCommand"));
    }
}
