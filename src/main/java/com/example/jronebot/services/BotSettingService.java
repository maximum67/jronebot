package com.example.jronebot.services;

import com.example.jronebot.models.BotSetting;
import com.example.jronebot.repositories.BotSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotSettingService implements ApplicationRunner {

    private final BotSettingRepository botSettingRepository;
    private final TurbochargerService turbochargerService;
    private final TelegramUserService telegramUserService;
    private BotSession botSession;

    public List<BotSetting> list(){ return botSettingRepository.findAll();}

    public void updateBotSetting(BotSetting botSetting){ botSettingRepository.save(botSetting);}

    public void updateBotSettingById(BotSetting botSetting, Long id){
        BotSetting botSetting1 = botSettingRepository.getReferenceById(id);
        botSetting1.setTokenBot(botSetting.getTokenBot());
        botSetting1.setBotName(botSetting.getBotName());
        botSetting1.setActiveBot(botSetting.isActiveBot());
        botSettingRepository.save(botSetting1);
    }

    public void updateBotActive(Long id, Boolean active) {
        BotSetting botSetting = botSettingRepository.getReferenceById(id);
        botSetting.setActiveBot(active);
        botSettingRepository.save(botSetting);
        if (active) {
          runBot(botSetting, turbochargerService, telegramUserService);
        }else{
           stopBot();
        }
    }
    public void deleteBotById(Long id){
            botSettingRepository.deleteById(id);
        }

    public BotSetting getBotSettingById(Long id){
        List<BotSetting> list = botSettingRepository.findAll();
        for (BotSetting botSetting: list){
            if (botSetting.getId()==id) return botSetting;
        }
        return  null;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (BotSetting bot : new BotSettingService(botSettingRepository, turbochargerService, telegramUserService).list()) {
            if (bot.isActiveBot()) {
               runBot(bot, turbochargerService,telegramUserService);
            }
        }
    }

    public void runBot(BotSetting botSetting, TurbochargerService turbochargerService, TelegramUserService telegramUserService){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSession = botsApi.registerBot(new JroneTelegramBot(botSetting, turbochargerService,telegramUserService));
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    public void stopBot(){
        if (botSession!=null) botSession.stop(); }
}
