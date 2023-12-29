package com.nhnacademy.shoppingmall.product_category.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product_category.domain.ProductCategory;
import com.nhnacademy.shoppingmall.product_category.repository.ProductCategoryRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {


    @Override
    public int save(ProductCategory productCategory) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        // 카테고리는 최대 3개까지만 가능
        if (countByProductId(productCategory.getProductId()) >= 3) {
            throw new RuntimeException("카테고리는 최대 3개까지만 가능합니다.");
        }

        String sql = "INSERT INTO Products_Categories VALUES (?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setInt(1, productCategory.getProductId());
            psmt.setInt(2, productCategory.getCategoryId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAllByProductId(int productId) {
        if (countByProductId(productId) < 1) {
            throw new RuntimeException("해당 상품의 카테고리가 존재하지 않습니다.");
        }

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "DELETE FROM Products_Categories WHERE product_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    @Override
//    public int update(ProductCategory productCategory) {
//
//        if (countByProductId(productCategory.getProductId()) < 1) {
//            throw new RuntimeException("해당 상품의 카테고리가 존재하지 않습니다.");
//        }
//
//        Connection connection = DbConnectionThreadLocal.getConnection();
//
//        String sql = "UPDATE Products_Categories SET category_id = ? WHERE product_id = ?";
//
//        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
//            psmt.setInt(1, productCategory.getCategoryId());
//            psmt.setInt(2, productCategory.getProductId());
//
//            return psmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public int countByProductId(int productId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT COUNT(*) FROM Products_Categories WHERE product_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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
    public List<ProductCategory> findAllByProductId(int productId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM Products_Categories WHERE product_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            List<ProductCategory> productCategories = new ArrayList<>();

            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                productCategories.add(
                        new ProductCategory(
                                rs.getInt("product_id"),
                                rs.getInt("category_id")));
            }

            return productCategories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
