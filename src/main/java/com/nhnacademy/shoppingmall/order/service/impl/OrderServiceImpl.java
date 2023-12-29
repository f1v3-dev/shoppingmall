package com.nhnacademy.shoppingmall.order.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderDescription;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getOrder(int orderId) {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(orderId);
        return optionalOrder.orElse(null);
    }

    @Override
    public void saveOrder(Order order) {
        int result = orderRepository.save(order);
        if (result < 1) {
            throw new RuntimeException("saveOrder error");
        }
    }

    @Override
    public void deleteOrder(int orderId) {
        if (orderRepository.countByOrderId(orderId) < 1) {
            throw new RuntimeException("해당 주문은 존재하지 않습니다.");
        }

        int result = orderRepository.deleteByOrderId(orderId);
        if (result < 1) {
            throw new RuntimeException("deleteOrder error");
        }
    }

    @Override
    public Page<Order> findAllByUserId(String userId, int page, int pageSize) {

        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        return orderRepository.findByUserId(userId, offset, limit);
    }

    @Override
    public int getSizeByUserId(String userId) {

        return orderRepository.countByUserId(userId);
    }

    @Override
    public List<OrderDescription> findOrderListByUserId(String userId) {
        return orderRepository.findOrderListByUserId(userId);
    }
}
