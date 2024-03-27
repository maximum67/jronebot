package com.example.jronebot.services;

import com.example.jronebot.models.TelegramUser;
import com.example.jronebot.repositories.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;

    public List<TelegramUser> list(){
        if (telegramUserRepository.findAll().isEmpty()){
            return null;
        }else {
            return telegramUserRepository.findAll();
        }
    }

    public void writeTelegramUserMessage(TelegramUser telegramUser){
        telegramUserRepository.save(telegramUser);
    }

    public void deleteTelegramUserById(Long id){
        telegramUserRepository.deleteById(id);
    }

    public List<TelegramUser> listByTelegramUserId(TelegramUser telegramUser){
        return telegramUserRepository.findAllByTelegramUserId(telegramUser.getTelegramUserId());
    }

}
