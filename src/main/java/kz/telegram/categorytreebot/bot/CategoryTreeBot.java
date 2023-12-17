package kz.telegram.categorytreebot.bot;

import kz.telegram.categorytreebot.config.TelegramBotProperties;
import kz.telegram.categorytreebot.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@Slf4j
public class CategoryTreeBot extends TelegramLongPollingBot {

    private final TelegramBotProperties properties;
    private List<TelegramBotCommandHandler> commandHandlers;
    private final CategoryService categoryService;

    public CategoryTreeBot(TelegramBotProperties properties, List<TelegramBotCommandHandler> commandHandlers, CategoryService categoryService) {
        super(properties.getToken());
        this.properties = properties;
        this.commandHandlers = commandHandlers;
        this.categoryService = categoryService;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            Long userId = update.getMessage().getFrom().getId();

            commandHandlers.stream().filter(handler -> handler.shouldHandle(text))
                    .findFirst().ifPresentOrElse(handler -> {
                                String handledText = handler.handle(text, userId);
                                sendMessage(chatId, handledText);
                            },
                            () -> sendMessage(chatId, "Неверный ввод команд. Введите /help для подробной информации"));

        }
    }


    @Override
    public String getBotUsername() {
        return properties.getName();
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
