package com.food.ordering.system.order.service.dataaccess.outbox.restaurantapproval.mapper;

import com.food.ordering.system.order.service.dataaccess.outbox.restaurantapproval.entity.ApprovalOutboxEntity;
import com.food.ordering.system.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import org.springframework.stereotype.Component;

@Component
public class ApprovalOutboxDataAccessMapper {
    public ApprovalOutboxEntity orderApprovalOutboxMessageToApprovalOutboxEntity(
            OrderApprovalOutboxMessage orderApprovalOutboxMessage) {
        return ApprovalOutboxEntity.builder()
                .id(orderApprovalOutboxMessage.getId())
                .sagaId(orderApprovalOutboxMessage.getSagaId())
                .type(orderApprovalOutboxMessage.getType())
                .createdAt(orderApprovalOutboxMessage.getCreatedAt())
                .payload(orderApprovalOutboxMessage.getPayload())
                .outboxStatus(orderApprovalOutboxMessage.getOutboxStatus())
                .orderStatus(orderApprovalOutboxMessage.getOrderStatus())
                .sagaStatus(orderApprovalOutboxMessage.getSagaStatus())
                .version(orderApprovalOutboxMessage.getVersion())
                .build();
    }

    public OrderApprovalOutboxMessage approvalOutboxEntityToOrderApprovalOutboxMessage(
            ApprovalOutboxEntity approvalOutboxEntity) {
        return OrderApprovalOutboxMessage.builder()
                .id(approvalOutboxEntity.getId())
                .sagaId(approvalOutboxEntity.getSagaId())
                .type(approvalOutboxEntity.getType())
                .createdAt(approvalOutboxEntity.getCreatedAt())
                .payload(approvalOutboxEntity.getPayload())
                .outboxStatus(approvalOutboxEntity.getOutboxStatus())
                .orderStatus(approvalOutboxEntity.getOrderStatus())
                .sagaStatus(approvalOutboxEntity.getSagaStatus())
                .version(approvalOutboxEntity.getVersion())
                .build();
    }
}