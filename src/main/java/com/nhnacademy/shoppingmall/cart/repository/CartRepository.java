package com.nhnacademy.shoppingmall.cart.repository;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import java.util.List;
import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findByCartId(int cartId);

    int save(Cart cart);

    int deleteByCartId(int cartId);

    int update(Cart cart);

    int countByCartId(int cartId);

    int countByUserId(String userId);

    List<Cart> getCartListByUserId(String userId);
}
