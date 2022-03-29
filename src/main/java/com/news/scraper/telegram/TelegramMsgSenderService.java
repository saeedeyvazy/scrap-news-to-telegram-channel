package com.news.scraper.telegram;

import com.news.scraper.ReplacementSentenceEnum;
import com.news.scraper.api.TelegramFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TelegramMsgSenderService {

    private final String chatId;
    private final TelegramFeignClient telegramFeignClient;

    public TelegramMsgSenderService(TelegramFeignClient telegramFeignClient,
                                    @Value("${telegram.chat.id}") String chatId) {

        this.telegramFeignClient = telegramFeignClient;
        this.chatId = chatId;

    }

    public void sendMsg(String msgText, String title, String photo) {
        telegramFeignClient.sendPhotoWithCaption(chatId, prettifyMessage(msgText, title), photo, "MARKDOWN");
    }

    private String prettifyMessage(String msgText, String title) {
        msgText = msgText.replace(ReplacementSentenceEnum.SENT1.toString(), "").replace(ReplacementSentenceEnum.SENT2.toString(), "");;
        return "\n*" + title + "*\n\n" + msgText;
    }
}
