package com.nhnacademy.shoppingmall.point_history.repository.impl;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point_history.domain.PointHistory;
import com.nhnacademy.shoppingmall.point_history.repository.PointHistoryRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PointHistoryRepositoryImpl implements PointHistoryRepository {
    @Override
    public List<PointHistory> findByUserId(String userId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM PointHistory WHERE user_id = ? ORDER BY registration_date DESC";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            List<PointHistory> pointHistories = new ArrayList<>();

            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                PointHistory pointHistory = new PointHistory(
                        rs.getInt("point_id"),
                        rs.getString("user_id"),
                        rs.getInt("point"),
                        rs.getString("description"),
                        rs.getTimestamp("registration_date").toLocalDateTime()
                );
                pointHistories.add(pointHistory);
            }

            return pointHistories;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int save(PointHistory pointHistory) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        // pointId=9999, userId='user', point=1000, description='테스트 포인트 내역입니다.', dateUpdated=2023-12-08T21:30:31
        String sql = "INSERT INTO PointHistory (user_id, point, description) VALUES (?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            psmt.setString(1, pointHistory.getUserId());
            psmt.setInt(2, pointHistory.getPoint());
            psmt.setString(3, pointHistory.getDescription());

            int result = psmt.executeUpdate();

            ResultSet generatedKeys = psmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                pointHistory.setPointId(generatedKeys.getInt(1));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int deleteByPointHistoryId(int pointHistoryId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "DELETE FROM PointHistory WHERE point_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, pointHistoryId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<PointHistory> findByUserIdAndDescription(String userId, String description) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM PointHistory WHERE user_id = ? AND description = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setString(2, description);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                PointHistory pointHistory = new PointHistory(
                        rs.getInt("point_id"),
                        rs.getString("user_id"),
                        rs.getInt("point"),
                        rs.getString("description"),
                        rs.getTimestamp("registration_date").toLocalDateTime()
                );
                return Optional.of(pointHistory);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.empty();
    }
}
