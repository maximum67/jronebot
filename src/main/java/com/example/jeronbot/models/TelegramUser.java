package com.example.jeronbot.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="telegramUser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TelegramUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="dateMessage")
    private String dateMessage;

    @Column(name="messageId")
    private Integer messageId;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="telegramUserId" )
    private Long telegramUserId;

    @Column(name="isBot")
    private boolean isBot;

    @Column(name="textMessage")
    private String textMessage;

    @Column(name="comment")
    private String comment;

}
