package com.example.jeronbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="botSetting")
@RequiredArgsConstructor
@Getter
@Setter
public class BotSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="botName")
    private String botName;

    @Column(name="tokenBot",unique = true)
    private String tokenBot;

    @Column(name="activeBot")
    private boolean activeBot;
}
