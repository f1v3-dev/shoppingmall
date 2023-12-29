package com.nhnacademy.shoppingmall.cart.service;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import java.util.List;

public interface CartService {

    Cart getCart(int cartId);

    void saveCart(Cart cart);

    void deleteCart(int cartId);

    void updateCart(Cart cart);

    List<Cart> getCartListByUserId(String userId);

}
