package com.example.jeronbot.services;

import com.example.jeronbot.models.BotSetting;
import com.example.jeronbot.models.TelegramUser;
import com.example.jeronbot.models.Turbocharger;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class JroneTelegramBot extends TelegramLongPollingBot {

    private BotSetting botSetting;
    private TurbochargerService turbochargerService;
    private TelegramUserService telegramUserService;

    public JroneTelegramBot(BotSetting botSetting, TurbochargerService turbochargerService, TelegramUserService telegramUserService) {
        this.botSetting = botSetting;
        this.turbochargerService = turbochargerService;
        this.telegramUserService = telegramUserService;
   }



    @Override
    public void onUpdateReceived(Update update) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        SendMessage message = new SendMessage();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(false);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
//        replyKeyboardMarkup.setIsPersistent(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow firstKeyboardRow = new KeyboardRow();
        firstKeyboardRow.add("/help");
//        KeyboardRow secondKeyboardRow = new KeyboardRow();
//        secondKeyboardRow.add("По номеру производителя авто");
        keyboardRows.add(firstKeyboardRow);
//        keyboardRows.add(secondKeyboardRow);
//
        replyKeyboardMarkup.setKeyboard(keyboardRows);

// --------------------------------------------------------------------------------------------

//            message.setChatId(update.getMessage().getChatId().toString());
//            message.setParseMode(ParseMode.HTML);   //включаем форматирование в данном случае через HTML
//            message.setText(dateFormat.format(Long.valueOf(update.getMessage().getDate()+"000")) + "\n"+
//                    update.getMessage().getFrom().getFirstName() + "\n"+
//                    update.getMessage().getFrom().getLastName() + "\n" +
//                    "ID сообщения: " + update.getMessage().getMessageId() + "\n" +
//                    "ID пользователя: "+ update.getMessage().getFrom().getId() + "\n" +
//                    "Это бот: " + update.getMessage().getFrom().getIsBot() + "\n" +
//                    "Сообщение: " +update.getMessage().getText());
//            try {
//                execute(message); // Отправляем сообщение
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }

//-----------------------------------------------------------------------------------------------


        if (update.hasMessage()) {
            if (update.getMessage().getText().equalsIgnoreCase("/help")) {
                message.setChatId(update.getMessage().getChatId().toString());
                message.setParseMode(ParseMode.HTML);   //включаем форматирование в данном случае через HTML
                message.setText("<b>Подбор запчастей JRONE</b>"+"\n"+"\n"+
                        "<i>Чтобы подобрать запчасти JRONE для турбокомпрессора введите номер производителя турбокомпрессора</i>" +
                        "<i> или номер производителя автомобиля</i>"+"\n"+
                        "\n"+"<a href = 'https://turbiny-smolensk.ru/'>МодаАвто</a>");
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
                StringBuilder str = new StringBuilder();
                String stringComment = "";

                List<Turbocharger> list = turbochargerService.getTurbochargerByOeNo(update.getMessage().getText());

                if (!list.isEmpty()) {
                    for (Turbocharger turbocharger : list) {
                        str.append("Производитель  ").append(turbocharger.getTurboMaker());
                        str.append("\n");
                        str.append("Модель  ").append(turbocharger.getTurboModel());
                        str.append("\n");
                        str.append("Номер  ").append(turbocharger.getTurboOeNo());
                        str.append("\n");
                        str.append("Марка авто  ").append(turbocharger.getBrand());
                        str.append("\n");
                        str.append("Модификация авто  ").append(turbocharger.getMakerModel());
                        str.append("\n");
                        str.append("Тип двигателя  ").append(turbocharger.getEngine());
                        str.append("\n");
                        str.append("Артикул автопроизводителя  ").append(turbocharger.getVehicleOeNo());
                        str.append("\n");
                        str.append("Номер JRONE  ").append(turbocharger.getJroneNo());
                        str.append("\n--- ");
                        str.append("Картридж  ").append(turbocharger.getChra());
                        str.append("\n--- ");
                        str.append("Вал с колесом  ").append(turbocharger.getShaftAndWheels());
                        str.append("\n--- ");
                        str.append("Колесо компрессора ").append(turbocharger.getCompressorWheels());
                        str.append("\n--- ");
                        str.append("Уплотнительная пластина  ").append(turbocharger.getSealPlates());
                        str.append("\n--- ");
                        str.append("Комплект упорных хомутов  ").append(turbocharger.getThrustCollarKits());
                        str.append("\n--- ");
                        str.append("Упорный подшипник  ").append(turbocharger.getThrustBearings());
                        str.append("\n--- ");
                        str.append("Цапфовые и шарикоподшипники  ").append(turbocharger.getJournalAndBallBearings());
                        str.append("\n--- ");
                        str.append("Корпус подшипника  ").append(turbocharger.getBearingHousings());
                        str.append("\n--- ");
                        str.append("Теплозащитный экран  ").append(turbocharger.getHeatShields());
                        str.append("\n--- ");
                        str.append("Актуатор в сборе  ").append(turbocharger.getActuatorAssy());
                        str.append("\n--- ");
                        str.append("Комплект прокладок  ").append(turbocharger.getGasketKits());
                        str.append("\n--- ");
                        str.append("Сопловое кольцо (\"геометрия\") в сборе  ").append(turbocharger.getNozzleRingAssy());
                        str.append("\n--- ");
                        str.append("Сервисный комплект  ").append(turbocharger.getServiceKits());
                        str.append("\n==========================\n");
                    }
                    message.setText(String.valueOf(str));
                    stringComment = "Успешный поиск";
                }else {
                    message.setText("Номер не верный или требует уточнения");
                    stringComment = "Номер не верный";
                }
                    try {
                        execute(message); // Отправляем сообщение
                        saveTelegramUser(update, dateFormat, stringComment);//логируем запрос
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            }
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


    private void saveTelegramUser(Update update, DateFormat dateFormat, String stringComment) {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setFirstName(update.getMessage().getFrom().getFirstName());
        telegramUser.setLastName(update.getMessage().getFrom().getLastName());
        telegramUser.setTelegramUserId(update.getMessage().getFrom().getId());
        telegramUser.setMessageId(update.getMessage().getMessageId());
        telegramUser.setBot(update.getMessage().getFrom().getIsBot());
        telegramUser.setComment(stringComment);
        telegramUser.setTextMessage(update.getMessage().getText());
        telegramUser.setDateMessage(dateFormat.format(Long.valueOf(update.getMessage().getDate()+"000")));

        telegramUserService.writeTelegramUserMessage(telegramUser);
   }

}
