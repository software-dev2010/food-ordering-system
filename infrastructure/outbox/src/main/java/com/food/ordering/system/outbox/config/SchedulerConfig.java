package com.food.ordering.system.outbox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    // I will not create any bean here, but you may create a specific ObjectMapper bean if you need
    // custom ObjectMapper configuration. I use ObjectMapper for json serialization of the domain event,
    // with spring-boot-starter-json dependency, a default ObjectMapper bean is created automatically
    // and using this default bean is enough.
}