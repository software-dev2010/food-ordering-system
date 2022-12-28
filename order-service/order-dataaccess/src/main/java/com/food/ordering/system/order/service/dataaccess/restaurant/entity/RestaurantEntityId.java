package com.food.ordering.system.order.service.dataaccess.restaurant.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntityId implements Serializable {

    private UUID restaurantId;
    private UUID productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantEntityId)) return false;
        RestaurantEntityId that = (RestaurantEntityId) o;
        return getRestaurantId().equals(that.getRestaurantId()) && getProductId().equals(that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRestaurantId(), getProductId());
    }
}