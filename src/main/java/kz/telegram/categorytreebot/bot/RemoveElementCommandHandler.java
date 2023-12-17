package kz.telegram.categorytreebot.bot;

import kz.telegram.categorytreebot.exception.CategoryNotFoundException;
import kz.telegram.categorytreebot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RemoveElementCommandHandler implements TelegramBotCommandHandler {

    public static final String REMOVE_ELEMENT_COMMAND = "/removeElement";

    private final CategoryService categoryService;

    @Override
    public String handle(String command, Long userId) {

        final var textToHandle = command.substring(REMOVE_ELEMENT_COMMAND.length()).trim();

        try {
            categoryService.deleteCategory(textToHandle, userId);
            return "Категория успешно удалена";
        } catch (CategoryNotFoundException e) {
            log.error("Category {} not found", textToHandle);
            return "Категория не найдена";
        }

    }

    @Override
    public boolean shouldHandle(String command) {
        return command.startsWith(REMOVE_ELEMENT_COMMAND);
    }
}
