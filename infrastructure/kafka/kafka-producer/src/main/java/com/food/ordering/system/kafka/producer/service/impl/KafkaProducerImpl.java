package com.food.ordering.system.kafka.producer.service.impl;

import com.food.ordering.system.kafka.producer.exception.KafkaProducerException;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;
import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase>
        implements KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    @Override
    public void send(String topicName,
                     K key,
                     V message,
                     ListenableFutureCallback<SendResult<K, V>> callback) {
        log.info("Sending message={} to topic={}", message, topicName);
        try {
            ListenableFuture<SendResult<K, V>> kafkaResultFuture =
                    kafkaTemplate.send(topicName, key, message);

            // I use a callback here because the send method is a non-blocking asynchronous call,
            // so it will not return a result immediately. Instead, it requires a callback method
            // to be called later asynchronously
            kafkaResultFuture.addCallback(callback);
        } catch (KafkaException ex) {
            log.error("Error on Kafka producer with key: {}, message: {} and exception: {}",
                    key, message, ex);
            throw new KafkaProducerException(
                    "Error on Kafka producer with key: " + key + " and message: " + message);
        }
    }

    // The close method is called when application is shutting down
    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing Kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}