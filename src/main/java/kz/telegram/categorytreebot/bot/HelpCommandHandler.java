package kz.telegram.categorytreebot.bot;

import org.springframework.stereotype.Component;

@Component
public class HelpCommandHandler implements TelegramBotCommandHandler {

    public static final String HELP_COMMAND = "/help";
    public static final String HELP_MSG = """
                            /viewTree - Дерево категории отобразиться в структурированном виде
                            /addElement <название элемента> - Добавление нового корневого элемента
                            /addElement <родительский элемент> <название элемента> - Добавление дочернего элемента к существующему элементу
                            /removeElement <название элемента> - Удаление элемента, при удалении родительского элемента, все дочерние элементы также будут удалены.
                            /help -  Список всех доступных команд и краткое их описание
                            """;

    @Override
    public String handle(String command, Long userId) {
        return HELP_MSG;
    }

    @Override
    public boolean shouldHandle(String command) {
        return command.toLowerCase().startsWith(HELP_COMMAND);
    }
}
