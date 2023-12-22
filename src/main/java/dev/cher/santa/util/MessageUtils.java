package dev.cher.santa.util;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class MessageUtils {
    public static void sendPhoto(TelegramLongPollingBot bot, long chatId, String image) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(String.valueOf(chatId));
        photo.setPhoto(new InputFile(image));

        try {
            bot.execute(photo);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    public static void executeEditMessageText(TelegramLongPollingBot bot, String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    public static Message executeMessage(TelegramLongPollingBot bot, SendMessage message) {
        try {
            return bot.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
            return null;
        }

    }

    public static void prepareAndSendMessage(TelegramLongPollingBot bot, long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(EmojiParser.parseToUnicode(textToSend));

        executeMessage(bot, message);
    }
}
