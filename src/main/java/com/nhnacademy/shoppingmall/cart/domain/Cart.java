package com.nhnacademy.shoppingmall.cart.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Cart {

    private Integer cartId;
    private String userId;
    private Integer productId;
    private Integer quantity;
    private LocalDateTime dateCreated;

    public Cart(Integer cartId, String userId, Integer productId, Integer quantity, LocalDateTime dateCreated) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.dateCreated = dateCreated;
    }

    public Cart(Integer cartId, String userId, Integer productId, Integer quantity) {
        this(cartId, userId, productId, quantity, LocalDateTime.now().withNano(0));
    }

    public Cart(String userId, Integer productId, Integer quantity) {
        this(null, userId, productId, quantity, LocalDateTime.now().withNano(0));
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        return Objects.equals(cartId, cart.cartId) && Objects.equals(userId, cart.userId) &&
                Objects.equals(productId, cart.productId) && Objects.equals(quantity, cart.quantity) &&
                Objects.equals(dateCreated, cart.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, userId, productId, quantity, dateCreated);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId='" + userId + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", dateCreated=" + dateCreated +
                '}';
    }
}