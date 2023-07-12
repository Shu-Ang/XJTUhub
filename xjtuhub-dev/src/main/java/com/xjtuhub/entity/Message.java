package com.xjtuhub.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Message {
    private Integer messageId;

    private String senderId;

    private String receiverId;

    private Date time;

    private String messageContent;

}