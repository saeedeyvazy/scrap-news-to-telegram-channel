package com.news.scraper.telegram;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Service
public class TelegramMsgSenderService {
    private String telegramApiUrl;

    public TelegramMsgSenderService(@Value("${telegram.api}") String telegramApiUrl) {
        this.telegramApiUrl = telegramApiUrl;
    }

    public void sendMsg(String msgText, String title, String photo) throws IOException {

        String transformedTelegramApiUrl =  prettifyMessage(msgText, title, photo);

        URL url = new URL(transformedTelegramApiUrl);
        URLConnection conn = url.openConnection();

        StringBuilder sb = new StringBuilder();
        InputStream is = new BufferedInputStream(conn.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String inputLine = "";
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        String response = sb.toString();
    }

    private String prettifyMessage(String msgText, String title, String photoUrl) {
        return telegramApiUrl.replace("[PHOTO]", photoUrl).replace("[MESSAGE_TEXT]", "%0A *" + title + "* %0A%0A" +  msgText);


    }
}
