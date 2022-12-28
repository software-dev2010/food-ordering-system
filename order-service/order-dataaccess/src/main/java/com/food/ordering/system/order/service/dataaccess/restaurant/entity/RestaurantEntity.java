package com.food.ordering.system.order.service.dataaccess.restaurant.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

// I will use a materialized view from the restaurant db to query restaurant information in the order domain layer
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RestaurantEntityId.class)
@Table(name = "order_restaurant_m_view", schema = "restaurant")
@Entity
public class RestaurantEntity {
    // The primary key will consist of those 2 fields
    @Id
    private UUID restaurantId;
    @Id
    private UUID productId;
    private String restaurantName;
    private Boolean restaurantActive;
    private String productName;
    private BigDecimal productPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantEntity)) return false;
        RestaurantEntity that = (RestaurantEntity) o;
        return getRestaurantId().equals(that.getRestaurantId()) && getProductId().equals(that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRestaurantId(), getProductId());
    }
}