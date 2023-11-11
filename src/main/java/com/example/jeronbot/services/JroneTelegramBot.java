package com.example.jeronbot.services;

import com.example.jeronbot.models.BotSetting;
import com.example.jeronbot.models.Turbocharger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class JroneTelegramBot extends TelegramLongPollingBot {

    private final BotSetting botSetting;

    private TurbochargerService turbochargerService;

    public JroneTelegramBot(BotSetting botSetting) {
        this.botSetting = botSetting;
    }

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage message = new SendMessage();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(false);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow firstKeyboardRow = new KeyboardRow();
        firstKeyboardRow.add("/help");
//        KeyboardRow secondKeyboardRow = new KeyboardRow();
//        secondKeyboardRow.add("По номеру производителя авто");
        keyboardRows.add(firstKeyboardRow);
//        keyboardRows.add(secondKeyboardRow);
//
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        if (update.hasMessage()) {
            if (update.getMessage().getText().equalsIgnoreCase("/help")) {
                message.setChatId(update.getMessage().getChatId().toString());
                message.setParseMode(ParseMode.HTML);   //включаем форматирование в данном случае через HTML
                message.setText("<b>Подбор запчастей JRONE</b>"+"\n"+"\n"+
                        "<i>Чтобы подобрать запчаст JRONE для турбокомпрессора введите номер производителя турбокомпрессора" +
                        " или номер производителя автомобиля</i>"+
                        "\n"+"<a href = 'https://modaauto.ru'>Ссылка</a>");
                try {
                    execute(message); // Отправляем сообщение
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }else if (update.getMessage().getText().equalsIgnoreCase("/start")) {
                message.setChatId(update.getMessage().getChatId().toString());
                message.setParseMode(ParseMode.HTML);
                message.setText("<i>Подбор запчастей JRONE</i>");
//                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
//
//                List <List<InlineKeyboardButton>> buttons = new ArrayList<>();
//                List<InlineKeyboardButton> buttons1 = new ArrayList<>();
//
//                InlineKeyboardButton button = new InlineKeyboardButton();
//                InlineKeyboardButton button1 = new InlineKeyboardButton();
//                button.setText("Номер производителя турбин");
//                button.setCallbackData("По номеру производителя турбин");
//                button1.setText("Номер производителя авто");
//                button1.setCallbackData("По номеру производителя авто");
//                buttons1.add(button);
//                buttons1.add(button1);
//                buttons.add(buttons1);
//
//                markup.setKeyboard(buttons);
//                message.setReplyMarkup(markup);
                try {
                    execute(message); // Отправляем сообщение
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                message.setChatId(update.getMessage().getChatId().toString());
                String str = "Text";

                List<Turbocharger> list = turbochargerService.getTurbochargerByOeNo(update.getMessage().getText());

                if (!list.isEmpty()) {
                    for (Turbocharger turbocharger : list) {
                        str += turbocharger.getTurboOeNo();
                        str += "\n";
                    }
                    message.setText(str);
                }
//                else {
//                    message.setText("Не корректный номер");
//                }
                    try {
                        execute(message); // Отправляем сообщение
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
//            }
        }else if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            if (callData.equals("По номеру производителя турбин")) {
                message.setChatId(update.getCallbackQuery().getMessage().getChatId());
                message.setText("Введите номер производителя турбин");
                try {
                    execute(message); // Отправляем сообщение
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }else if (callData.equals("По номеру производителя авто")) {
                message.setChatId(update.getCallbackQuery().getMessage().getChatId());
                message.setText("Введите номер производителя авто");
                try {
                    execute(message); // Отправляем сообщение
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                message.setChatId(update.getCallbackQuery().getMessage().getChatId());
                message.setText("Воспользуйтесь помощью");
                try {
                    execute(message); // Отправляем сообщение
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public synchronized void answerCallbackQuery(String callbackId,String message){
//        AnswerCallbackQuery answer = new AnswerCallbackQuery();
//        answer.setCallbackQueryId(callbackId);
//        answer.setText(message);
//        answer.setShowAlert(true);
//        try{
//            answerCallbackQuery(answer);
//        }catch(TelegramApiException e){
//            e.printStackTrace();
//        }
//    }

    private void setInline() {

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }


    @Override
    public String getBotUsername(){
        return botSetting.getBotName();
    }
    @Override
    public String getBotToken(){
        return botSetting.getTokenBot();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

}
