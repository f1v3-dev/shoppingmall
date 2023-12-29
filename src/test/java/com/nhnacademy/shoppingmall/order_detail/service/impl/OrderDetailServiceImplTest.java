package com.nhnacademy.shoppingmall.order_detail.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import com.nhnacademy.shoppingmall.order_detail.domain.OrderDetail;
import com.nhnacademy.shoppingmall.order_detail.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.order_detail.service.OrderDetailService;
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
class OrderDetailServiceImplTest {

    OrderDetailRepository orderDetailRepository = Mockito.mock(OrderDetailRepository.class);

    OrderDetailService orderDetailService = new OrderDetailServiceImpl(orderDetailRepository);

    OrderDetail testOrderDetail = new OrderDetail(1, 1, 1, 1000);

    @Test
    @DisplayName("주문 상세 조회 : 주문 번호, 상품 번호로 조회")
    void getOrderDetail() {
        Mockito.when(orderDetailRepository.findOrderDetail(anyInt(), anyInt()))
                .thenReturn(Optional.of(testOrderDetail));

        OrderDetail orderDetail =
                orderDetailService.getOrderDetail(testOrderDetail.getOrderId(), testOrderDetail.getProductId());
        Assertions.assertEquals(testOrderDetail, orderDetail);

        Mockito.verify(orderDetailRepository, Mockito.times(1)).findOrderDetail(anyInt(), anyInt());
    }

    @Test
    @DisplayName("주문 상세 조회 : 유효하지 않는 주문번호, 상품번호로 조회")
    void getOrderDetail_Invalid() {
        Mockito.when(orderDetailRepository.findOrderDetail(anyInt(), anyInt())).thenReturn(Optional.empty());

        OrderDetail orderDetail =
                orderDetailService.getOrderDetail(testOrderDetail.getOrderId(), testOrderDetail.getProductId());
        Assertions.assertNull(orderDetail);

        Mockito.verify(orderDetailRepository, Mockito.times(1)).findOrderDetail(anyInt(), anyInt());
    }

    @Test
    @DisplayName("주문 상세 등록 : saveOrderDetail")
    void saveOrderDetail() {
        Mockito.when(orderDetailRepository.save(any())).thenReturn(1);
        orderDetailService.saveOrderDetail(testOrderDetail);
        Mockito.verify(orderDetailRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("주문 상세 삭제 : deleteOrderDetail")
    void deleteOrderDetail() {
        Mockito.when(orderDetailRepository.deleteOrderDetail(anyInt(), anyInt())).thenReturn(1);
        orderDetailService.deleteOrderDetail(testOrderDetail.getOrderId(), testOrderDetail.getProductId());
        Mockito.verify(orderDetailRepository, Mockito.times(1)).deleteOrderDetail(anyInt(), anyInt());
    }
}