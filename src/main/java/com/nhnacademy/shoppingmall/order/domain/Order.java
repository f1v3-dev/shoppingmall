package com.nhnacademy.shoppingmall.order.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private Integer orderId;
    private String userId;
    private LocalDateTime orderDate;
    private LocalDateTime shipDate;

    public Order(Integer orderId, String userId, LocalDateTime orderDate, LocalDateTime shipDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate.withNano(0);

        if (shipDate != null) {
            this.shipDate = shipDate.withNano(0);
        } else {
            this.shipDate = null;
        }
    }

    public Order(String userId, LocalDateTime orderDate, LocalDateTime shipDate) {
        this(null, userId, orderDate, shipDate);
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDateTime shipDate) {
        this.shipDate = shipDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(userId, order.userId) &&
                Objects.equals(orderDate, order.orderDate) && Objects.equals(shipDate, order.shipDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, orderDate, shipDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId='" + userId + '\'' +
                ", orderDate=" + orderDate +
                ", shipDate=" + shipDate +
                '}';
    }

}
