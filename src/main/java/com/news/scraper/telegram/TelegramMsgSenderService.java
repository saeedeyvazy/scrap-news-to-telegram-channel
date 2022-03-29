package com.news.scraper.telegram;

import com.news.scraper.ReplacementSentenceEnum;
import com.news.scraper.api.TelegramFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

        msgText = String.join(".\n\n\uD83D\uDD39",msgText.split("\\."));
        return "\n*" + title + "*\n\n \uD83D\uDD39 " + msgText + "\n\n @havades\\_ruz\\_iran";
    }
}
