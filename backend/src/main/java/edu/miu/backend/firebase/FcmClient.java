package edu.miu.backend.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FcmClient {
    public FcmClient(FcmSettings settings) {
        Path p = Paths.get("/Users/abrorkhamidov/Desktop/MIU/WAA/waa_project/backend/src/main/resources/waa-alumni-firebase.json");
        try (InputStream serviceAccount = Files.newInputStream(p)) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.error("init fcm", e);
        }
    }

    public void sendPersonalMessage(String clientToken, Map<String, String> data)
            throws InterruptedException, ExecutionException {

        Message message = Message.builder().putAllData(data).setToken(clientToken)
                .setNotification(Notification.builder().setTitle(data.get("title"))
                        .setBody(data.get("body")).build())
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        System.out.println("Sent message: " + response);
    }
}
