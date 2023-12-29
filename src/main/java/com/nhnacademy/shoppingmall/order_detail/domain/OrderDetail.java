package com.nhnacademy.shoppingmall.order_detail.domain;

import java.util.Objects;

public class OrderDetail {
    /**
     * CREATE TABLE OrderDetails
     * (
     *     order_id   int,
     *     product_id int,
     *     quantity   int         NOT NULL,
     *     unit_cost  decimal(15) NOT NULL,
     *
     *     CONSTRAINT pk_OrderDetails PRIMARY KEY (order_id, product_id),
     *     CONSTRAINT fk_OrderDetails_Orders FOREIGN KEY (order_id) REFERENCES Orders (order_id),
     *     CONSTRAINT fk_OrderDetails_Products FOREIGN KEY (product_id) REFERENCES Products (product_id)
     * ) ENGINE = InnoDB
     *   DEFAULT CHARSET = utf8mb4
     *   COLLATE = utf8mb4_0900_ai_ci COMMENT ='주문상세';
     */

    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer unitCost;

    public OrderDetail(Integer orderId, Integer productId, Integer quantity, Integer unitCost) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitCost = unitCost;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnitCost(Integer unitCost) {
        this.unitCost = unitCost;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getUnitCost() {
        return unitCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId) &&
                Objects.equals(quantity, that.quantity) && Objects.equals(unitCost, that.unitCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, quantity, unitCost);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", unitCost=" + unitCost +
                '}';
    }
}
