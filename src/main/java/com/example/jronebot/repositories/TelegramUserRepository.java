package com.example.jronebot.repositories;

import com.example.jronebot.models.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    List<TelegramUser> findAllByTelegramUserId(Long telegramUserId);


}


