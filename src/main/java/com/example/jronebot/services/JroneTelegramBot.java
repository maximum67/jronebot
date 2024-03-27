package com.example.jronebot.services;

import com.example.jronebot.models.BotSetting;
import com.example.jronebot.models.TelegramUser;
import com.example.jronebot.models.Turbocharger;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow firstKeyboardRow = new KeyboardRow();
        firstKeyboardRow.add("/help");
        keyboardRows.add(firstKeyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        if (update.hasMessage()&&update.getMessage().hasText()) {
            if (update.getMessage().getText().equalsIgnoreCase("/help")) {
                message.setChatId(update.getMessage().getChatId().toString());
                message.setParseMode(ParseMode.HTML);   //включаем форматирование в данном случае через HTML
                message.setText("<b>Подбор запчастей JRONE</b>"+"\n"+"\n"+
                        "<i>Введите номер производителя турбокомпрессора</i>" +
                        "<i> или номер производителя автомобиля</i>"+"\n"+
                        "\n"+"<a href = 'https://turbiny-smolensk.ru/'>МодаАвто</a>");
                try {
                    execute(message); // Отправляем сообщение
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                message.setChatId(update.getMessage().getChatId().toString());
                StringBuilder str = new StringBuilder();
                String stringComment = "";

                List<Turbocharger> list = turbochargerService.getTurbochargerByOeNo(update.getMessage().getText()
                                                              .replaceAll("[^[0-9A-Za-z/]]",""));
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
                        str.append("Год выпуска  ").append(turbocharger.getYear());
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
        }
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
