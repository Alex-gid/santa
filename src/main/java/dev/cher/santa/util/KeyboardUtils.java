package dev.cher.santa.util;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardUtils {
    //две кнопки в тексте
    public static InlineKeyboardMarkup createYesNoButtonsInMessage(String buttonOneName, String buttonOneCallback, String buttonTwoName, String buttonTwoCallback) {

        InlineKeyboardButton yesButton = new InlineKeyboardButton();
        yesButton.setText(buttonOneName);
        yesButton.setCallbackData(buttonOneCallback);
        InlineKeyboardButton noButton = new InlineKeyboardButton();
        noButton.setText(buttonTwoName);
        noButton.setCallbackData(buttonTwoCallback);

        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        rowInLine.add(yesButton);
        rowInLine.add(noButton);

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(rowInLine);
        markupInline.setKeyboard(rowsInline);

        return markupInline;

    }

    //Две кнопки в клавиатуре
    public static ReplyKeyboardMarkup createButtonsInKeyboard(TelegramLongPollingBot bot, Long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Все подарки в разработке, пожалуйста обратитесь попозже Хо-Хо-Хо");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("Все подарки в разработке, пожалуйста обратитесь попозже Хо-Хо-Хо");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Ваш текст сообщения");
        message.setReplyMarkup(createButtonsInKeyboard(bot, chatId));
        MessageUtils.executeMessage(bot, message);
        message.setReplyMarkup(keyboardMarkup);
        return keyboardMarkup;
    }
}
