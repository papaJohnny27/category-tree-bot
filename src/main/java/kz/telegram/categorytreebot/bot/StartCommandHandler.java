package kz.telegram.categorytreebot.bot;

import org.springframework.stereotype.Component;

@Component
public class StartCommandHandler implements TelegramBotCommandHandler {

    public static final String START_COMMAND = "/start";
    public static String START_MSG = """
                            Бот позволяет пользователям
                             создавать, просматривать и удалять дерево категорий.
                             Введите команду /help для просмотра доступных комманд
                            """;

    @Override
    public String handle(String command, Long userId) {
        return START_MSG;
    }

    @Override
    public boolean shouldHandle(String command) {
        return command.toLowerCase().startsWith(START_COMMAND);
    }
}
