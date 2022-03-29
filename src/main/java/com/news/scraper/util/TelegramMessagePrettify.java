package com.news.scraper.util;

import com.news.scraper.ReplacementSentenceEnum;
import org.springframework.stereotype.Component;

@Component("TelegramMessagePrettify")
public class TelegramMessagePrettify implements MessagePrettier {

    @Override
    public  String prettifyMessage(String msgText, String title) {
        msgText = msgText.replace(ReplacementSentenceEnum.SENT1.toString(), "").replace(ReplacementSentenceEnum.SENT2.toString(), "");;

        msgText = String.join(".\n\n\uD83D\uDD39",msgText.split("\\."));
        return "\n*" + title + "*\n\n \uD83D\uDD39 " + msgText + "\n\n @havades\\_ruz\\_iran";
    }
}
