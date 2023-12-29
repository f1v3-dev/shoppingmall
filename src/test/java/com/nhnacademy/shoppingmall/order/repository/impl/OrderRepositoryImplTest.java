package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class OrderRepositoryImplTest {

    OrderRepository orderRepository = new OrderRepositoryImpl();

    Order testOrder;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testOrder = new Order("user", LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        orderRepository.save(testOrder);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("주문 조회 : 주문 번호로 조회")
    void findByOrderId() {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(testOrder.getOrderId());
        Assertions.assertEquals(testOrder, optionalOrder.get());
    }

    @Test
    @DisplayName("주문 조회 : 유효하지 않는 주문 번호로 조회")
    void findByInvalidOrderId() {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(Integer.MAX_VALUE);
        Assertions.assertEquals(Optional.empty(), optionalOrder);
    }

    @Test
    @DisplayName("주문 등록 : save 테스트")
    void saveOrder() {
        Order newOrder = new Order("user", LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        int result = orderRepository.save(newOrder);
        Assertions.assertAll(() -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newOrder, orderRepository.findByOrderId(newOrder.getOrderId()).get()));
    }


    @Test
    @DisplayName("주문 삭제 : deleteByOrderId 테스트")
    void deleteByOrder() {
        int result = orderRepository.deleteByOrderId(testOrder.getOrderId());
        Assertions.assertAll(() -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(orderRepository.findByOrderId(testOrder.getOrderId()).isPresent()));
    }

    @Test
    @Disabled
    @DisplayName("주문 조회 : User ID로 주문 조회 - Page 반환이여서 테스트가 어려움")
    void findByUserId() {
        Page<Order> orderPage = orderRepository.findByUserId(testOrder.getUserId(), 0, 10);
//        List<Order> orderList = orderPage.getContent();
//        Assertions.assertEquals(testOrder, orderList.get(0));
    }

    @Test
    @DisplayName("주문 개수 조회 : Order ID로 주문 개수 조회")
    void countByOrderId() {
        int count = orderRepository.countByOrderId(testOrder.getOrderId());
        Assertions.assertEquals(1, count);
    }

    @Test
    @Disabled
    @DisplayName("주문 개수 조회 : User ID로 주문 개수 조회")
    void countByUserId() {
        int count = orderRepository.countByUserId(testOrder.getUserId());
        Assertions.assertEquals(1, count);
    }

    @Test
    @Disabled
    @DisplayName("주문 리스트 조회 : User ID로 주문 리스트 조회 (JOIN -> 테스트 ?)")
    void findOrderListByUserId() {
//        List<Order> orderList = orderRepository.findOrderListByUserId();
//        Assertions.assertEquals(testOrder, orderList.get(0));
    }


}