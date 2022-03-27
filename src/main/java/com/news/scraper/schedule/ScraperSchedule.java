package com.news.scraper.schedule;

import com.news.scraper.telegram.TelegramMsgSenderService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

@Component
public class ScraperSchedule {
    private final TelegramMsgSenderService telegramMsgSenderService;
    private String url;

    @Autowired
    public ScraperSchedule(TelegramMsgSenderService telegramMsgSenderService,
                           @Value("${url}") String url) {
        this.telegramMsgSenderService = telegramMsgSenderService;
        this.url = url;
    }

    @Scheduled(cron = "${cron.scrap}")
    public void scrap() {
        try {
            Document document = Jsoup.connect(url).get();
            Element element = document.getElementsByClass("row no-gutters").get(3);
            Elements elementsByTag = element.getElementsByTag("a");
            elementsByTag.forEach(item -> {
                try {
                    Document detailNewsDoc = Jsoup.connect(item.attr("href")).get();
                    telegramMsgSenderService.sendMsg(detailNewsDoc.getElementsByClass("post-body" +
                            "-ac").first().text(), item.text());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void selfSign() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
        }

    }
}
