package com.example.jeronbot.repositories;

import com.example.jeronbot.models.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    List<TelegramUser> findAllByTelegramUserId(Long telegramUserId);


}


