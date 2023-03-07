package com.food.ordering.system.order.service.dataaccess.outbox.payment.mapper;

import com.food.ordering.system.order.service.dataaccess.outbox.payment.entity.PaymentOutboxEntity;
import com.food.ordering.system.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import org.springframework.stereotype.Component;

@Component
public class PaymentOutboxDataAccessMapper {

    public PaymentOutboxEntity orderPaymentOutboxMessageToPaymentOutboxEntity(
            OrderPaymentOutboxMessage orderPaymentOutboxMessage) {

        return PaymentOutboxEntity.builder()
                .id(orderPaymentOutboxMessage.getId())
                .sagaId(orderPaymentOutboxMessage.getSagaId())
                .createdAt(orderPaymentOutboxMessage.getCreatedAt())
                .type(orderPaymentOutboxMessage.getType())
                .payload(orderPaymentOutboxMessage.getPayload())
                .orderStatus(orderPaymentOutboxMessage.getOrderStatus())
                .sagaStatus(orderPaymentOutboxMessage.getSagaStatus())
                .outboxStatus(orderPaymentOutboxMessage.getOutboxStatus())
                .version(orderPaymentOutboxMessage.getVersion())
                .build();
    }

    public OrderPaymentOutboxMessage paymentOutboxEntityToOrderPaymentOutboxMessage(
            PaymentOutboxEntity paymentOutboxEntity) {

        return OrderPaymentOutboxMessage.builder()
                .id(paymentOutboxEntity.getId())
                .sagaId(paymentOutboxEntity.getSagaId())
                .outboxStatus(paymentOutboxEntity.getOutboxStatus())
                .orderStatus(paymentOutboxEntity.getOrderStatus())
                .sagaStatus(paymentOutboxEntity.getSagaStatus())
                .type(paymentOutboxEntity.getType())
                .payload(paymentOutboxEntity.getPayload())
                .createdAt(paymentOutboxEntity.getCreatedAt())
                .version(paymentOutboxEntity.getVersion())
                .build();
    }
}