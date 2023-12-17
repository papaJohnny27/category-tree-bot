package kz.telegram.categorytreebot.config;

import kz.telegram.categorytreebot.bot.CategoryTreeBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotInitializer {

    private final CategoryTreeBot categoryTreeBot;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(categoryTreeBot);
        } catch (TelegramApiException e) {
            log.error("Could not register a bot", e);
        }
    }

}
