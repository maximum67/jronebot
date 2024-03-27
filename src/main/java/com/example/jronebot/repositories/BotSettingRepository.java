package com.example.jronebot.repositories;

import com.example.jronebot.models.BotSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotSettingRepository extends JpaRepository<BotSetting, Long> {
}
