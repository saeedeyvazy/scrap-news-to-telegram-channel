package com.news.scraper.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class TelegramMsgSenderService {
    private String telegramApiUrl;

    public TelegramMsgSenderService(@Value("${telegram.api}") String telegramApiUrl) {
        this.telegramApiUrl = telegramApiUrl;
    }

    public void sendMsg(String msgText, String title) throws IOException {
        String transformedTelegramApiUrl = telegramApiUrl.replace("[MESSAGE_TEXT]", msgText);
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
}
