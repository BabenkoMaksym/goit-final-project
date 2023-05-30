package ua.goit.finalProj2.telegram;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {



    final BotConfig config;

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
        return config.getBotName();
    }
}
