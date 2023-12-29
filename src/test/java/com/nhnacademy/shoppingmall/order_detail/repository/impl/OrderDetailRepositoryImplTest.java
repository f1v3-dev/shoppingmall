package com.nhnacademy.shoppingmall.order_detail.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order_detail.domain.OrderDetail;
import com.nhnacademy.shoppingmall.order_detail.repository.OrderDetailRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class OrderDetailRepositoryImplTest {

    OrderDetailRepository orderDetailRepository = new OrderDetailRepositoryImpl();
    OrderDetail testOrderDetail;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testOrderDetail = new OrderDetail(1, 1, 1, 1000);
        orderDetailRepository.save(testOrderDetail);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("주문 상세 조회 : 주문 번호, 상품 번호로 조회")
    void findOrderDetail() {
        Optional<OrderDetail> orderDetail =
                orderDetailRepository.findOrderDetail(testOrderDetail.getOrderId(), testOrderDetail.getProductId());

        Assertions.assertEquals(testOrderDetail, orderDetail.get());
    }

    @Test
    @DisplayName("주문 상세 조회 : 유효하지 않은 주문번호, 상품번호로 조회")
    void findInvalidOrderDetail() {
        Optional<OrderDetail> orderDetail =
                orderDetailRepository.findOrderDetail(Integer.MAX_VALUE, testOrderDetail.getProductId());
        Assertions.assertEquals(Optional.empty(), orderDetail);

        orderDetail =
                orderDetailRepository.findOrderDetail(testOrderDetail.getOrderId(), Integer.MAX_VALUE);
        Assertions.assertEquals(Optional.empty(), orderDetail);

        orderDetail =
                orderDetailRepository.findOrderDetail(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Assertions.assertEquals(Optional.empty(), orderDetail);
    }

    @Test
    @DisplayName("주문 상세 등록 : save 테스트")
    void saveOrderDetail() {
        OrderDetail newOrderDetail = new OrderDetail(2, 2, 2, 2000);
        int result = orderDetailRepository.save(newOrderDetail);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newOrderDetail,
                        orderDetailRepository.findOrderDetail(newOrderDetail.getOrderId(), newOrderDetail.getProductId()).get())
        );
    }

    @Test
    @DisplayName("주문 상세 삭제 : deleteByOrderId 테스트")
    void deleteByOrderDetail() {
        int result = orderDetailRepository.deleteOrderDetail(testOrderDetail.getOrderId(), testOrderDetail.getProductId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(orderDetailRepository.findOrderDetail(testOrderDetail.getOrderId(), testOrderDetail.getProductId()).isPresent())
        );
    }
}