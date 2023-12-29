package com.nhnacademy.shoppingmall.order_detail.service;

import com.nhnacademy.shoppingmall.order_detail.domain.OrderDetail;

public interface OrderDetailService {

    OrderDetail getOrderDetail(int orderId, int productId);

    void saveOrderDetail(OrderDetail orderDetail);

    void deleteOrderDetail(int orderId, int productId);


}
