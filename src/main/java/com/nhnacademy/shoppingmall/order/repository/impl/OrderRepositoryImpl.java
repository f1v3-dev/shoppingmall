package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderDescription;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public Optional<Order> findByOrderId(int orderId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM Orders WHERE order_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, orderId);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                Order order = new Order(rs.getInt("order_id"), rs.getString("user_id"),
                        rs.getTimestamp("order_date").toLocalDateTime(),
                        rs.getTimestamp("ship_date") != null ? rs.getTimestamp("ship_date").toLocalDateTime() : null);

                return Optional.of(order);
            }
        } catch (SQLException e) {
            log.error("findByOrderId error", e);
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(Order order) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "INSERT INTO Orders (user_id, order_date, ship_date) VALUES (?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, order.getUserId());
            psmt.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            psmt.setTimestamp(3, order.getShipDate() != null ? Timestamp.valueOf(order.getShipDate()) : null);

            int result = psmt.executeUpdate();

            ResultSet generatedKeys = psmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setOrderId(generatedKeys.getInt(1));
            }

            return result;
        } catch (SQLException e) {
            log.error("save error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByOrderId(int orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "DELETE FROM Orders WHERE order_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setInt(1, orderId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteByOrderId error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Order> findByUserId(String userId, int offset, int limit) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM Orders WHERE user_id = ? ORDER BY order_date DESC LIMIT ?, ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setString(1, userId);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);

            List<Order> orderList = new ArrayList<>();

            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(rs.getInt("order_id"), rs.getString("user_id"),
                        rs.getTimestamp("order_date").toLocalDateTime(),
                        rs.getTimestamp("ship_date") != null ? rs.getTimestamp("ship_date").toLocalDateTime() : null);

                orderList.add(order);
            }

            return new Page<>(orderList, orderList.size());
        } catch (SQLException e) {
            log.error("findByUserId error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByOrderId(int orderId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT COUNT(*) FROM Orders WHERE order_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, orderId);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("countByOrderId error", e);
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int countByUserId(String userId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT COUNT(*) FROM Orders WHERE user_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("countByUserId error", e);
            throw new RuntimeException(e);
        }
        return 0;

    }

    @Override
    public List<OrderDescription> findOrderListByUserId(String userId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql =
                "SELECT o.user_id, o.order_date, o.ship_date, d.quantity, d.unit_cost, p.model_name, p.product_image "
                        + "FROM Orders AS o INNER JOIN OrderDetails AS d ON o.order_id = d.order_id "
                        + "INNER JOIN Products AS p ON p.product_id = d.product_id "
                        + "WHERE o.user_id = ? "
                        + "ORDER BY o.order_id DESC";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            List<OrderDescription> orderList = new ArrayList<>();

            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                OrderDescription order = new OrderDescription(
                        rs.getString("user_id"),
                        rs.getTimestamp("order_date").toLocalDateTime(),
                        rs.getTimestamp("ship_date") != null ? rs.getTimestamp("ship_date").toLocalDateTime() : null,
                        rs.getInt("quantity"),
                        rs.getInt("unit_cost"),
                        rs.getString("model_name"),
                        rs.getString("product_image")
                );

                orderList.add(order);
            }

            return orderList;
        } catch (SQLException e) {
            log.error("findOrderListByUserId error", e);
            throw new RuntimeException(e);
        }
    }
}
