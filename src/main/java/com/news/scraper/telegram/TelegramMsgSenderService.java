package com.news.scraper.telegram;

import com.news.scraper.api.TelegramFeignClient;
import com.news.scraper.util.MessagePrettifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TelegramMsgSenderService {

    private final String chatId;
    private final TelegramFeignClient telegramFeignClient;
    private final MessagePrettifier messagePrettifier;


    public TelegramMsgSenderService(TelegramFeignClient telegramFeignClient,
                                    @Value("${telegram.chat.id}") String chatId, @Qualifier("TelegramMessagePrettify") MessagePrettifier messagePrettifier) {

        this.telegramFeignClient = telegramFeignClient;
        this.chatId = chatId;
        this.messagePrettifier = messagePrettifier;
    }

    public void sendMsg(String msgText, String title, String photo) {
        telegramFeignClient.sendPhotoWithCaption(chatId, messagePrettifier.prettifyMessage(msgText, title), photo, "MARKDOWN");
    }

}
