package com.news.scraper.instagram;

import com.github.instagram4j.instagram4j.IGClient;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class InstagramMessageSenderService {
    private final IGClient client;

    @Autowired
    public InstagramMessageSenderService(IGClient client) {
        this.client = client;
    }
    @PostConstruct
    public void sendPhoto() throws IOException {
        if (client != null) {
            URL url = new URL("https://saednews.com/storage/media-center/images/ac-image-T71648366325oh.jpeg");
            BufferedImage img = ImageIO.read(url);
            File file = new File("downloaded.jpg");
            ImageIO.write(img, "jpg", file);
            client.actions()
                    .timeline()
                    .uploadPhoto(file, "My Caption")
                    .thenAccept(response -> {
                        System.out.println("Successfully uploaded photo!");
                    })
                    .join(); // block current thread until complete
        }
        else
            System.out.println("Client is NULL!!");
    }
}
