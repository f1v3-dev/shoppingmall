package com.nhnacademy.shoppingmall.order_detail.service.impl;

import com.nhnacademy.shoppingmall.order_detail.domain.OrderDetail;
import com.nhnacademy.shoppingmall.order_detail.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.order_detail.service.OrderDetailService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public OrderDetail getOrderDetail(int orderId, int productId) {

        Optional<OrderDetail> orderDetail = orderDetailRepository.findOrderDetail(orderId, productId);
        return orderDetail.orElse(null);
    }

    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {

        int result = orderDetailRepository.save(orderDetail);
        if (result < 1) {
            throw new RuntimeException("saveOrderDetail error");
        }

    }

    @Override
    public void deleteOrderDetail(int orderId, int productId) {

        int result = orderDetailRepository.deleteOrderDetail(orderId, productId);
        if (result < 1) {
            throw new RuntimeException("deleteOrderDetail error");
        }
    }
}
