package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderDescription;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findByOrderId(int orderId);

    int save(Order order);

    int deleteByOrderId(int orderId);

    Page<Order> findByUserId(String userId, int offset, int limit);

    int countByOrderId(int orderId);

    int countByUserId(String userId);

    List<OrderDescription> findOrderListByUserId(String userId);
}
