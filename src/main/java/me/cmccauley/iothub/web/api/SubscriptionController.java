package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.services.SubscriptionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    public SubscriptionService getSubscriptionService() {
        return subscriptionService;
    }
}
