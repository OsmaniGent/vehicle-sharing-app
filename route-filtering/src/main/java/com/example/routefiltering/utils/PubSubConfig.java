package com.example.routefiltering.utils;

import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class PubSubConfig {

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("updateRouteInputChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "updateRoute-sub");
        adapter.setOutputChannel(inputChannel);
        return adapter;
    }

    @Bean
    public MessageChannel updateRouteInputChannel() {
        return new DirectChannel();
    }

}