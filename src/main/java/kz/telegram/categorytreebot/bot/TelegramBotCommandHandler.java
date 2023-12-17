package kz.telegram.categorytreebot.bot;

public interface TelegramBotCommandHandler {

    /**
     * Handle user command
     * @param command command
     * @param userId user who sends request
     * @return result of executed command
     */
    String handle(String command, Long userId);

    /**
     * Evaluate whether command should execute or not
     * @param command text command
     * @return true, if should be executed, otherwise false
     */
    boolean shouldHandle(String command);
}
