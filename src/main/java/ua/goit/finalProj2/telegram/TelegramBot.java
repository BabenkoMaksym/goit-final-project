package ua.goit.finalProj2.telegram;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {


    final BotConfig config;

    public TelegramBot() {
        super("6220505870:AAG9f6zwSUWGkVUB6hGPYJOW7dz1CwAvAP0");
        this.config = new BotConfig();
    }


    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getFrom().getId();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hello");
        sendApiMethodAsync(message);
    }

    @Override
    public String getBotUsername() {
        return "List_of_Thoughts_bot";
    }
}
