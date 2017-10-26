import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class FirstBot extends TelegramLongPollingBot {
    String[] buttonTexts = {"first", "second", "third", "fourth", "fifth", "sixth"};
    SendMessage basemessage;
    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("/start")) {
                basemessage = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Hello! Welcome to Java Bot 1!");
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline;

                for(int i = 0; i < 6; i++){
                    String curText = buttonTexts[i] + " button string";
                    rowInline = new ArrayList<>();
                    rowInline.add(new InlineKeyboardButton().setText(curText).setCallbackData("update_msg_text" + i));
                    // Set the keyboard to the markup
                    rowsInline.add(rowInline);
                }

                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                basemessage.setReplyMarkup(markupInline);
                try {
                    execute(basemessage); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {

            }

        } else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if (call_data.startsWith("update_msg_text")) {
                String answer = "Chose " + buttonTexts[Integer.parseInt(call_data.substring("update_msg_text".length()))] + " button";
                EditMessageText new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText(answer);
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline;
                rowInline = new ArrayList<>();
                rowInline.add(new InlineKeyboardButton().setText("Back").setCallbackData("back"));
                markupInline.setKeyboard(rowsInline);
                new_message.setReplyMarkup(markupInline);
                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else if (call_data.equals("back")){
                try {
                    execute(basemessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "BarsTestyBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "448569681:AAHLnpg_2sKYFC8FCQA8n5LRj1CvvBNPXvI";
    }
}
