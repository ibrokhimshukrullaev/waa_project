package edu.miu.backend.service;

import edu.miu.backend.entity.Subscriber;
import edu.miu.backend.firebase.FcmClient;
import edu.miu.backend.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class NotificationService {

    @Autowired
    private FcmClient fcmClient;

    @Autowired
    private SubscriberRepository subscriberRepository;

    public void sendMessage(Integer receiverId) throws ExecutionException, InterruptedException {
        Subscriber subscriber = subscriberRepository.findBySubscriberId(receiverId);
        Map<String, String> data = new HashMap<>();
        data.put("title", "Job Subscription Alert");
        data.put("body", "Student applied to your job advertisement");
        fcmClient.sendPersonalMessage(subscriber.getToken(), data);
    }

    public void sendNotificationToStudent(int studentId) throws ExecutionException, InterruptedException {
        Subscriber subscriber = subscriberRepository.findBySubscriberId(studentId);
        Map<String, String> data = new HashMap<>();
        data.put("title", "New Job Add alert");
        data.put("body", "New job advertisement you may interested in");
        if (subscriber != null) {
            fcmClient.sendPersonalMessage(subscriber.getToken(), data);
        }
    }
}
