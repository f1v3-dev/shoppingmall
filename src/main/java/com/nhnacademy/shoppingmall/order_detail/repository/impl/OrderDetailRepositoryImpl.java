package com.nhnacademy.shoppingmall.order_detail.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order_detail.domain.OrderDetail;
import com.nhnacademy.shoppingmall.order_detail.repository.OrderDetailRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDetailRepositoryImpl implements OrderDetailRepository {
    @Override
    public Optional<OrderDetail> findOrderDetail(int orderId, int productId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM OrderDetails WHERE order_id = ? AND product_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, orderId);
            psmt.setInt(2, productId);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                OrderDetail orderDetail = new OrderDetail(
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getInt("unit_cost")
                );
                return Optional.of(orderDetail);
            }

        } catch (SQLException e) {
            log.debug("OrderDetail : findOrderDetail error = {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public int save(OrderDetail orderDetail) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "INSERT INTO OrderDetails VALUES(?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setInt(1, orderDetail.getOrderId());
            psmt.setInt(2, orderDetail.getProductId());
            psmt.setInt(3, orderDetail.getQuantity());
            psmt.setInt(4, orderDetail.getUnitCost());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.debug("OrderDetail : save error = {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int deleteOrderDetail(int orderId, int productId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "DELETE FROM OrderDetails WHERE order_id = ? AND product_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setInt(1, orderId);
            psmt.setInt(2, productId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.debug("OrderDetail : deleteOrderDetail error = {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
