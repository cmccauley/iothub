package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<?> createSubscription(@Valid @RequestBody Subscription subscription)
    {
        final Subscription createdSubscription = subscriptionService.createSubscription(subscription);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdSubscription.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{subscriptionId}")
    public Subscription getSubscriptionById(@PathVariable(value = "subscriptionId") Long subscriptionId) {
        return subscriptionService.getSubscriptionById(subscriptionId);
    }

    @GetMapping
    public Collection<Subscription> getAllSubscriptions()
    {
        return subscriptionService.getAllSubscriptions();
    }

    @DeleteMapping
    public void deleteSubscription(Long id){
        subscriptionService.deleteSubscription(id);
    }



    public SubscriptionService getSubscriptionService() {
        return subscriptionService;
    }
}
