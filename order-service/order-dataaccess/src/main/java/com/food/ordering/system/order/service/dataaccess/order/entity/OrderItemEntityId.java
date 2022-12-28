package com.food.ordering.system.order.service.dataaccess.order.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntityId implements Serializable {

    private Long id;
    private OrderEntity order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemEntityId)) return false;
        OrderItemEntityId that = (OrderItemEntityId) o;
        return getId().equals(that.getId()) && getOrder().equals(that.getOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrder());
    }
}