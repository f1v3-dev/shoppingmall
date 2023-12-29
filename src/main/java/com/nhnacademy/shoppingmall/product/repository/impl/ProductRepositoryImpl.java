package com.nhnacademy.shoppingmall.product.repository.impl;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Optional<Product> findByProductId(int productId) {

        // 상품의 아이디를 통해 상품을 조회하는 코드

        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ")
                .append("FROM Products ")
                .append("WHERE product_id = ?");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setInt(1, productId);

            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("model_number"),
                        rs.getString("model_name"),
                        rs.getString("product_image"),
                        rs.getInt("unit_cost"),
                        rs.getString("description")
                );

                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(Product product) {

        // productId => Auto Increment -> 값을 넘기지 말자
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO Products")
                .append("(model_number, model_name, product_image, unit_cost, description) ")
                .append("VALUES (?, ?, ?, ?, ?)");

        log.debug("product save = {}", product);
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString(), RETURN_GENERATED_KEYS)) {

            psmt.setString(1, product.getModelNumber());
            psmt.setString(2, product.getModelName());
            psmt.setString(3, product.getProductImage());
            psmt.setDouble(4, product.getUnitCost());
            psmt.setString(5, product.getDescription());
            int result = psmt.executeUpdate();

            ResultSet generatedKeys = psmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setProductId(generatedKeys.getInt(1));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(int productId) {

        StringBuilder sql = new StringBuilder();

        sql.append("DELETE FROM Products ")
                .append("WHERE product_id = ?");

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setInt(1, productId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Product product) {

        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE Products ")
                .append("SET model_number = ?, model_name = ?, product_image = ?, unit_cost = ?, description = ? ")
                .append("WHERE product_id = ?");

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setString(1, product.getModelNumber());
            psmt.setString(2, product.getModelName());
            psmt.setString(3, product.getProductImage());
            psmt.setDouble(4, product.getUnitCost());
            psmt.setString(5, product.getDescription());
            psmt.setInt(6, product.getProductId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByProductId(int productId) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) ")
                .append("FROM Products ")
                .append("WHERE product_id = ?");

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {

            psmt.setInt(1, productId);

            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public int countByModelNumber(String modelNumber) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) ")
                .append("FROM Products ")
                .append("WHERE model_number = ?");

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setString(1, modelNumber);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }


    @Override
    public int getProductSize() {

        String sql = "SELECT count(*) FROM Products";

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public int getProductSizeByCategory(int categoryId) {

        String sql = "SELECT count(*) " +
                "FROM Products AS p INNER JOIN Products_Categories AS pc ON p.product_id = pc.product_id " +
                "WHERE category_id = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int getSearchProductSize(String name) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String escapeName = "%" + name + "%";

        String sql = "SELECT count(*) FROM Products WHERE model_name LIKE ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, escapeName);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return 0;
    }


    @Override
    public Optional<Product> findByModelNumber(String modelNumber) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM Products WHERE model_number = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setString(1, modelNumber);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("model_number"),
                        rs.getString("model_name"),
                        rs.getString("product_image"),
                        rs.getInt("unit_cost"),
                        rs.getString("description")
                );
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Page<Product> findAll(int offset, int limit) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Products ORDER BY product_id DESC LIMIT ?, ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);

            ResultSet rs = psmt.executeQuery();
            List<Product> productList = new ArrayList<>();

            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("model_number"),
                        rs.getString("model_name"),
                        rs.getString("product_image"),
                        rs.getInt("unit_cost"),
                        rs.getString("description")
                ));
            }

            return new Page<>(productList, productList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Product> findAllByModelName(String name, int offset, int limit) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String escapeName = "%" + name + "%";

        String sql = "SELECT * FROM Products WHERE model_name LIKE ? ORDER BY model_name ASC LIMIT ?, ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setString(1, escapeName);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);

            ResultSet rs = psmt.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                productList.add(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("model_number"),
                                rs.getString("model_name"),
                                rs.getString("product_image"),
                                rs.getInt("unit_cost"),
                                rs.getString("description")
                        )
                );
            }
            return new Page<>(productList, productList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Page<Product> findAllByCategoryId(int categoryId, int offset, int limit) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * " +
                "FROM Products AS p INNER JOIN Products_Categories AS pc ON p.product_id = pc.product_id " +
                "WHERE category_id = ? ORDER BY p.product_id DESC LIMIT ?, ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);

            ResultSet rs = psmt.executeQuery();

            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("model_number"),
                        rs.getString("model_name"),
                        rs.getString("product_image"),
                        rs.getInt("unit_cost"),
                        rs.getString("description")
                );

                productList.add(product);
            }

            return new Page<>(productList, productList.size());
        } catch (SQLException e) {
            throw new RuntimeException("Product - findAllByCategoryId ", e);
        }
    }
}
