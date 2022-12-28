package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class OrderKafkaMessageHelper {

    public <T> ListenableFutureCallback<SendResult<String, T>>
    getKafkaCallback(String paymentRequestTopicName, T paymentRequestAvroModel) {
        return new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error while sending PaymentRequestAvroModel message {} to topic {}",
                        paymentRequestAvroModel.toString(), paymentRequestTopicName);
            }

            @Override
            public void onSuccess(SendResult<String, PaymentRequestAvroModel> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received successful response from Kafka for order id: {}" +
                                " Topic: {} Partition: {} Offset: {} Timestamp: {}",
                        paymentRequestAvroModel.getOrderId(),
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp());
            }
        };
    }
}
