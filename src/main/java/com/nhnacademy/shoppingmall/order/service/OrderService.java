package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderDescription;
import java.util.List;

public interface OrderService {

    Order getOrder(int orderId);

    void saveOrder(Order order);

    void deleteOrder(int orderId);

    Page<Order> findAllByUserId(String userId, int page, int pageSize);

    int getSizeByUserId(String userId);

    List<OrderDescription> findOrderListByUserId(String userId);
}
