package com.nhnacademy.shoppingmall.order.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import java.time.LocalDateTime;
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
class OrderServiceImplTest {

    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    OrderService orderService = new OrderServiceImpl(orderRepository);

    Order testOrder =
            new Order(9999, "user", LocalDateTime.now(), LocalDateTime.now().plusDays(3));

    @Test
    @DisplayName("주문 조회 : getOrder")
    void getOrder() {
        Mockito.when(orderRepository.findByOrderId(anyInt())).thenReturn(Optional.of(testOrder));
        Order order = orderService.getOrder(testOrder.getOrderId());

        Assertions.assertEquals(testOrder, order);

        Mockito.verify(orderRepository, Mockito.times(1)).findByOrderId(anyInt());
    }

    @Test
    @DisplayName("주문 조회 : 존재하지 않는 주문일 경우 -> return null")
    void getOrder_Not_Found() {
        Mockito.when(orderRepository.findByOrderId(anyInt())).thenReturn(Optional.empty());
        Order order = orderService.getOrder(testOrder.getOrderId());
        Assertions.assertNull(order);
    }

    @Test
    @DisplayName("주문 저장 : saveOrdre")
    void saveOrder() {
        Mockito.when(orderRepository.save(any())).thenReturn(1);
        orderService.saveOrder(testOrder);
        Mockito.verify(orderRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("주문 삭제 : deleteOrder")
    void deleteOrder() {
        Mockito.when(orderRepository.countByOrderId(anyInt())).thenReturn(1);
        Mockito.when(orderRepository.deleteByOrderId(anyInt())).thenReturn(1);

        orderService.deleteOrder(testOrder.getOrderId());

        Mockito.verify(orderRepository, Mockito.times(1)).countByOrderId(anyInt());
        Mockito.verify(orderRepository, Mockito.times(1)).deleteByOrderId(anyInt());
    }

    @Test
    @DisplayName("주문 삭제 : order ID가 존재하지 않는 경우 -> exception")
    void deleteOrder_Exception() {
        Mockito.when(orderRepository.countByOrderId(anyInt())).thenReturn(0);

        Throwable throwable =
                Assertions.assertThrows(RuntimeException.class, () -> orderService.deleteOrder(testOrder.getOrderId()));

        log.debug("error = {}", throwable.getMessage());
    }
}