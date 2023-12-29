package com.nhnacademy.shoppingmall.cart.repository.impl;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class CartRepositoryImplTest {

    CartRepository cartRepository = new CartRepositoryImpl();
    Cart testCart;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testCart = new Cart("user", 1, 1);
        cartRepository.save(testCart);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("장바구니 조회 : 장바구니 ID로 조회")
    void findByCartId() {
        Optional<Cart> optionalCart = cartRepository.findByCartId(testCart.getCartId());
        Assertions.assertEquals(testCart, optionalCart.get());
    }

    @Test
    @DisplayName("장바구니 조회 : 존재하지 않은 장바구니 조회")
    void findByInvalidCartId() {
        Optional<Cart> optionalCart = cartRepository.findByCartId(100000);
        Assertions.assertEquals(Optional.empty(), optionalCart);
    }

    @Test
    @DisplayName("장바구니 등록 : 장바구니 등록 성공")
    void saveCart() {
        Cart newCart = new Cart("user", 3, 4);
        int result = cartRepository.save(newCart);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newCart, cartRepository.findByCartId(newCart.getCartId()).get())
        );
    }

    @Test
    @DisplayName("장바구니 등록 : 같은 회원이 같은 상품을 장바구니에 담은 경우 - 제약조건 오류")
    void saveCart_Fail() {

        Throwable throwable = Assertions.assertThrows(RuntimeException.class, () -> cartRepository.save(testCart));

        Assertions.assertTrue(
                throwable.getMessage().contains("Duplicate entry")
        );

        log.debug("error message = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("장바구니 삭제 : 장바구니 ID로 삭제")
    void deleteByCartId() {

        int result = cartRepository.deleteByCartId(testCart.getCartId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(cartRepository.findByCartId(testCart.getCartId()).isPresent())
        );
    }

    @Test
    @DisplayName("장바구니 삭제 : 존재하지 않은 장바구니 삭제")
    void deleteByInvalidCartId() {
        int result = cartRepository.deleteByCartId(Integer.MAX_VALUE);
        Assertions.assertEquals(0, result);
    }

    @Test
    @DisplayName("장바구니 수정 : 장바구니 수량 변경")
    void updateCart() {

        testCart.setQuantity(5);

        int result = cartRepository.update(testCart);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testCart, cartRepository.findByCartId(testCart.getCartId()).get())
        );
    }

    @Test
    @DisplayName("장바구니 개수 조회 : 유저 ID로 유저의 총 장바구니 조회")
    void countByUserId() {
        int result = cartRepository.countByUserId(testCart.getUserId());
        Assertions.assertEquals(1, result);

        Cart newCart = new Cart("user", 3, 4);
        cartRepository.save(newCart);
        result = cartRepository.countByUserId(testCart.getUserId());
        Assertions.assertEquals(2, result);
    }

    @Test
    @DisplayName("장바구니 개수 조회 : 유저 ID로 유저의 총 장바구니 조회 - 존재하지 않는 유저")
    void countByInvalidUserId() {
        int result = cartRepository.countByUserId("INVALID_USER_ID");
        Assertions.assertEquals(0, result);
    }

    @Test
    @Disabled
    @DisplayName("장바구니 리스트 조회 : 유저 ID로 유저의 장바구니 리스트 조회")
    void getCartListByUserId() {
        List<Cart> cartList = cartRepository.getCartListByUserId(testCart.getUserId());

        for (Cart cart : cartList) {
            log.debug("cart = {}", cart);
        }

        Assertions.assertEquals(testCart, cartList.get(0));
    }
}
