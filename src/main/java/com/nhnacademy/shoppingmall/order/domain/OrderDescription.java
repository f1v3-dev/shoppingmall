package com.nhnacademy.shoppingmall.order.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderDescription {
    /**
     * user_id, order_date, ship_date, quantity, unit_cost, model_name, product_image
     */

    private String userId;
    private LocalDateTime orderDate;
    private LocalDateTime shipDate;
    private int quantity;
    private int unitCost;
    private String modelName;
    private String productImage;

    public OrderDescription(String userId, LocalDateTime orderDate, LocalDateTime shipDate, int quantity, int unitCost,
                            String modelName, String productImage) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.modelName = modelName;
        this.productImage = productImage;
    }

    public OrderDescription(String userId, LocalDateTime orderDate, int quantity, int unitCost, String modelName,
                            String productImage) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.shipDate = null;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.modelName = modelName;
        this.productImage = productImage;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDescription that = (OrderDescription) o;
        return quantity == that.quantity && unitCost == that.unitCost && Objects.equals(userId, that.userId) &&
                Objects.equals(orderDate, that.orderDate) && Objects.equals(shipDate, that.shipDate) &&
                Objects.equals(modelName, that.modelName) &&
                Objects.equals(productImage, that.productImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orderDate, shipDate, quantity, unitCost, modelName, productImage);
    }

    @Override
    public String toString() {
        return "OrderDescription{" +
                "userId='" + userId + '\'' +
                ", orderDate=" + orderDate +
                ", shipDate=" + shipDate +
                ", quantity=" + quantity +
                ", unitCost=" + unitCost +
                ", modelName='" + modelName + '\'' +
                ", productImage='" + productImage + '\'' +
                '}';
    }
}
