package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<?> createSubscription(@Valid @RequestBody Subscription subscription) {
        final Subscription createdSubscription = subscriptionService.createSubscription(subscription);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdSubscription.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable(value = "subscriptionId") Long subscriptionId) {
        return Optional
                .ofNullable(subscriptionService.getSubscriptionById(subscriptionId))
                .map(topic -> ResponseEntity.ok().body(topic))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public Collection<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @DeleteMapping
    public void deleteSubscription(Long id) {
        subscriptionService.deleteSubscription(id);
    }


    public SubscriptionService getSubscriptionService() {
        return subscriptionService;
    }
}
