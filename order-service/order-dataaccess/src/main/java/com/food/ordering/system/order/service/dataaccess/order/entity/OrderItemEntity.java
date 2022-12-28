package com.food.ordering.system.order.service.dataaccess.order.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderItemEntityId.class)
@Table(name = "order_items")
@Entity

public class OrderItemEntity {
    // Here is a multi-column primary key
    // I can only provide uniqueness of this entity with id and orderId fields together.
    // The long id field will only provide a unique item in a specific order
    @Id
    private Long id;

    @Id
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private OrderEntity order;

    private UUID productId;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subTotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemEntity)) return false;
        OrderItemEntity that = (OrderItemEntity) o;
        return getId().equals(that.getId()) && getOrder().equals(that.getOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrder());
    }
}