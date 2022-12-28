package com.food.ordering.system.order.service.dataaccess.customer.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_customer_m_view", schema = "customer")  // I will use a materialized view from the customer db
@Entity
public class CustomerEntity {
    // I use this entity just to check if the customer exists or not

    @Id
    private UUID id;
}