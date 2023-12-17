package kz.telegram.categorytreebot.bot;

import kz.telegram.categorytreebot.TestDataUtil;
import kz.telegram.categorytreebot.dto.CategoryDto;
import kz.telegram.categorytreebot.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewTreeCommandHandlerTest {

    private final CategoryService categoryService = Mockito.mock(CategoryService.class);
    private final ViewTreeCommandHandler commandHandler =
            new ViewTreeCommandHandler(categoryService);


    @Test
    void handle_expectOk() {
        final Long userId = 1L;
        List<CategoryDto> categories = Arrays.asList(aCategoryDto(userId));
        Mockito.when(categoryService.getAllParentByUserId(userId)).thenReturn(categories);


        String handledText = commandHandler.handle("/viewTree", userId);

        String expected = """
└───parent
	├───child1
	└───child2
""";
        assertEquals(expected, handledText);
    }

    @Test
    void shouldHandle() {
        assertTrue(commandHandler.shouldHandle("/viewTree"));
        assertFalse(commandHandler.shouldHandle("/randomCommand"));
    }

    public static CategoryDto aCategoryDto(Long userId) {
        return CategoryDto.builder()
                .id(1L)
                .name("parent")
                .children(new HashSet<>())
                .children(Set.of(
                        CategoryDto.builder()
                                .id(1L)
                                .name("child1")
                                .children(new HashSet<>())
                                .build(),
                        CategoryDto.builder()
                                .id(2L)
                                .name("child2")
                                .children(new HashSet<>())
                                .build()))
                .build();
    }
}
