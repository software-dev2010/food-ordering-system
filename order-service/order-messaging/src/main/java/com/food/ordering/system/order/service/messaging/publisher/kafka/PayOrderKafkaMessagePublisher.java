package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayOrderKafkaMessagePublisher implements
        OrderPaidRestaurantPaymentRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();
        RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel =
                orderMessagingDataMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent);

        kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                orderId,
                restaurantApprovalRequestAvroModel,
                );
    }
}
