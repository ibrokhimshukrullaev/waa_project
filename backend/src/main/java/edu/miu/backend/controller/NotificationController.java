package edu.miu.backend.controller;

import edu.miu.backend.entity.Subscriber;
import edu.miu.backend.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
@CrossOrigin
public class NotificationController {

    private final SubscriberRepository subscriberRepository;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@RequestParam("token") String token, @RequestParam int subscriberId) {
        Subscriber s = new Subscriber();
        s.setToken(token);
        s.setSubscriberId(subscriberId);
        subscriberRepository.save(s);
    }

    @PostMapping("/unregister")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unregister(@RequestParam("token") String token, @RequestParam int subscriberId) {
        subscriberRepository.deleteBySubscriberId(subscriberId);
    }
}
