package com.nhnacademy.shoppingmall.cart.repository.impl;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
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
public class CartRepositoryImpl implements CartRepository {

    @Override
    public Optional<Cart> findByCartId(int cartId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM Cart WHERE cart_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, cartId);

            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                Cart cart = new Cart(
                        rs.getInt("cart_id"),
                        rs.getString("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("date_created").toLocalDateTime());

                return Optional.of(cart);
            }
        } catch (SQLException e) {
            log.error("findByCartId error = {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(Cart cart) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "INSERT INTO Cart (user_id, product_id, quantity, date_created) VALUES (?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            psmt.setString(1, cart.getUserId());
            psmt.setInt(2, cart.getProductId());
            psmt.setInt(3, cart.getQuantity());
            psmt.setTimestamp(4, Timestamp.valueOf(cart.getDateCreated()));

            int result = psmt.executeUpdate();

            ResultSet generatedKeys = psmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                cart.setCartId(generatedKeys.getInt(1));
            }

            return result;
        } catch (SQLException e) {
            log.error("save error = {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int deleteByCartId(int cartId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "DELETE FROM Cart WHERE cart_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, cartId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteByCartId error = {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int update(Cart cart) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "UPDATE Cart SET user_id = ?, product_id = ?, quantity = ?, date_created = ? WHERE cart_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setString(1, cart.getUserId());
            psmt.setInt(2, cart.getProductId());
            psmt.setInt(3, cart.getQuantity());
            psmt.setTimestamp(4, Timestamp.valueOf(cart.getDateCreated()));
            psmt.setInt(5, cart.getCartId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("update error = {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int countByCartId(int cartId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT COUNT(*) FROM Cart WHERE cart_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, cartId);

            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("countByCartId error = {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return 0;
    }

    @Override
    public int countByUserId(String userId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT COUNT(*) FROM Cart WHERE BINARY user_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("countByUserId error = {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Cart> getCartListByUserId(String userId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM Cart WHERE BINARY user_id = ? ORDER BY cart_id DESC";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            ResultSet rs = psmt.executeQuery();
            List<Cart> cartList = new ArrayList<>();

            while (rs.next()) {
                cartList.add(
                        new Cart(
                                rs.getInt("cart_id"),
                                rs.getString("user_id"),
                                rs.getInt("product_id"),
                                rs.getInt("quantity"),
                                rs.getTimestamp("date_created").toLocalDateTime()));
            }

            return cartList;
        } catch (SQLException e) {
            log.error("getCartListByUserId error = {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
