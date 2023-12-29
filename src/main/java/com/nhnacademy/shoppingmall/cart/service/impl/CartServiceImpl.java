package com.nhnacademy.shoppingmall.cart.service.impl;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.cart.service.CartService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCart(int cartId) {

        Optional<Cart> optionalCart = cartRepository.findByCartId(cartId);
        return optionalCart.orElse(null);
    }

    @Override
    public void saveCart(Cart cart) {
        int result = cartRepository.save(cart);
        if (result < 1) {
            throw new RuntimeException("CartService : saveCart error");
        }
    }

    @Override
    public void deleteCart(int cartId) {
        int result = cartRepository.deleteByCartId(cartId);
        if (result < 1) {
            throw new RuntimeException("CartService : deleteCart error");
        }
    }

    @Override
    public void updateCart(Cart cart) {
        int result = cartRepository.update(cart);
        if (result < 1) {
            throw new RuntimeException("CartService : updateCart error");
        }
    }

    @Override
    public List<Cart> getCartListByUserId(String userId) {

        return cartRepository.getCartListByUserId(userId);
    }
}
