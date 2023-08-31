package com.example.jeronbot.repositories;

import com.example.jeronbot.models.BotSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotSettingRepository extends JpaRepository<BotSetting, Long> {
}
