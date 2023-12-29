package com.nhnacademy.shoppingmall.category.repository.impl;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public Optional<Category> findByCategoryId(int categoryId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM Categories WHERE category_id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setInt(1, categoryId);

            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                Category category = new Category(rs.getInt("category_id"), rs.getString("category_name"));

                log.debug("Find Category = {}", category);
                return Optional.of(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(Category category) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "INSERT INTO Categories(category_name) VALUES(?)";
        try (PreparedStatement psmt = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            psmt.setString(1, category.getCategoryName());

            int result = psmt.executeUpdate();

            ResultSet generatedKeys = psmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setCategoryId(generatedKeys.getInt(1));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByCategoryId(int categoryId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setInt(1, categoryId);
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Category category) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "UPDATE Categories  SET category_name = ? WHERE category_id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, category.getCategoryName());
            psmt.setInt(2, category.getCategoryId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByCategoryId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT count(*) FROM Categories WHERE category_id = ?";
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
    public List<Category> getCategories() {
        List<Category> categories = new LinkedList<>();

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM Categories";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                categories.add(new Category(rs.getInt("category_id"), rs.getString("category_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    @Override
    public int countByCategoryName(String categoryName) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT count(*) FROM Categories WHERE category_name = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, categoryName);

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
    public int getCategorySize() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COUNT(*) FROM Categories";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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
    public Page<Category> findAll(int offset, int limit) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Categories ORDER BY category_id DESC LIMIT ?, ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);

            ResultSet rs = psmt.executeQuery();
            List<Category> categoryList = new ArrayList<>();

            while (rs.next()) {
                categoryList.add(new Category(rs.getInt("category_id"), rs.getString("category_name")));
            }

            return new Page<>(categoryList, categoryList.size());

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
