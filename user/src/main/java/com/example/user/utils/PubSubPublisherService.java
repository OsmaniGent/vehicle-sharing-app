package com.example.user.utils;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PubSubPublisherService {

    private final PubSubTemplate pubSubTemplate;

    @Autowired
    public PubSubPublisherService(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    public void publishMessage(String topicName, String message) {
        pubSubTemplate.publish(topicName, message);
    }
}
