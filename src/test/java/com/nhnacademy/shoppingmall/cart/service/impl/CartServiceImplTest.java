package com.nhnacademy.shoppingmall.cart.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.cart.service.CartService;
import com.nhnacademy.shoppingmall.cart.service.impl.CartServiceImpl;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    CartRepository cartRepository = Mockito.mock(CartRepository.class);
    CartService cartService = new CartServiceImpl(cartRepository);

    Cart testCart = new Cart(9999, "user", 1, 1);

    @Test
    @DisplayName("장바구니 조회 : 장바구니 ID로 조회")
    void findByCartId() {
        Mockito.when(cartRepository.findByCartId(anyInt())).thenReturn(Optional.of(testCart));
        cartService.getCart(testCart.getCartId());
        Mockito.verify(cartRepository, Mockito.times(1)).findByCartId(anyInt());
    }

    @Test
    @DisplayName("장바구니 저장 : saveCart")
    void saveCart() {
        Mockito.when(cartRepository.save(any())).thenReturn(1);
        cartService.saveCart(testCart);
        Mockito.verify(cartRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("장바구니 저장 : 동일한 상품이 들어온 경우 실패")
    void saveCart_Fail() {

        Throwable throwable = Assertions.assertThrows(RuntimeException.class, () -> cartService.saveCart(testCart));

        log.error("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("장바구니 삭제 : deleteCart")
    void deleteCart() {
        Mockito.when(cartRepository.deleteByCartId(anyInt())).thenReturn(1);
        cartService.deleteCart(testCart.getCartId());
        Mockito.verify(cartRepository, Mockito.times(1)).deleteByCartId(anyInt());
    }

    @Test
    @DisplayName("장바구니 삭제 : 존재하지 않는 장바구니 삭제")
    void deleteCart_Not_Exists() {
        Mockito.when(cartRepository.deleteByCartId(anyInt())).thenReturn(0);

        Throwable throwable =
                Assertions.assertThrows(RuntimeException.class, () -> cartService.deleteCart(testCart.getCartId()));

        log.error("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("장바구니 수정 : updateCart")
    void updateCart() {
        Mockito.when(cartRepository.update(any())).thenReturn(1);

        cartService.updateCart(testCart);

        Mockito.verify(cartRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("장바구니 수정 : 존재하지 않는 상품 업데이트")
    void updateCart_Not_Exists() {
        Mockito.when(cartRepository.update(any())).thenReturn(0);

        Throwable throwable =
                Assertions.assertThrows(RuntimeException.class, () -> cartService.deleteCart(testCart.getCartId()));

        log.error("error = {}", throwable.getMessage());
    }
}
