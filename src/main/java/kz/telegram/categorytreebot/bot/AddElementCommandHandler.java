package kz.telegram.categorytreebot.bot;

import kz.telegram.categorytreebot.exception.CategoryAlreadyExistsException;
import kz.telegram.categorytreebot.exception.CategoryNotFoundException;
import kz.telegram.categorytreebot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AddElementCommandHandler implements TelegramBotCommandHandler {

    public static final String ADD_ELEMENT_COMMAND = "/addElement";

    private final CategoryService categoryService;

    @Override
    public String handle(String command, Long userId) {

        final var textToHandle = command.substring(ADD_ELEMENT_COMMAND.length()).trim();

        String parent = getParent(textToHandle);
        String child = getChild(textToHandle);

        try {
            categoryService.addCategory(child, parent, userId);
            return "Категория успешно добавлена";
        } catch (CategoryAlreadyExistsException e) {
            log.error("Category already exists, categoryName: {}", child);
            return String.format("Категория %s уже существует", child);
        } catch (CategoryNotFoundException e) {
            log.error("Category {} not found", parent);
            return "Не найдена родительская категория: " + parent;
        } catch (Exception e) {
            log.error("Error during add category, parent: {}, child: {}, userId: {}", parent, child, userId, e);
            return "Непредвиденная ошибка при добавлении категории";
        }

    }

    @Override
    public boolean shouldHandle(String command) {
        return command.startsWith(ADD_ELEMENT_COMMAND);
    }

    private String getParent(String text) {
        String[] split = text.split("\\s+");

        if (split.length == 1) {
            return "";
        }

        return split[0];
    }

    private String getChild(String text) {

        String[] split = text.split("\\s+");
        if (split.length == 1) {
            return split[0];
        }

        return split[1];
    }

}
