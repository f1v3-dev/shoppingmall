package com.nhnacademy.shoppingmall.order_detail.repository;

import com.nhnacademy.shoppingmall.order_detail.domain.OrderDetail;
import java.util.Optional;

public interface OrderDetailRepository {

    Optional<OrderDetail> findOrderDetail(int orderId, int productId);

    int save(OrderDetail orderDetail);

    int deleteOrderDetail(int orderId, int productId);
}
