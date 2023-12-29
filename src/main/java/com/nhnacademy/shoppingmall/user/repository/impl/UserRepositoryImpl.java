package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuffer sql = new StringBuffer();
        sql.append(
                        "SELECT user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at ")
                .append("FROM Users ")
                .append("WHERE BINARY user_id = ? AND user_password = ?");


        log.debug("sql = {}", sql);
        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setString(1, userId);
            psmt.setString(2, userPassword);

            log.debug("psmt = {}", psmt);

            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_birth"),
                        User.Auth.valueOf(rs.getString("user_auth")),
                        rs.getInt("user_point"),
                        Objects.nonNull(rs.getTimestamp("created_at")) ?
                                rs.getTimestamp("created_at").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("latest_login_at")) ?
                                rs.getTimestamp("latest_login_at").toLocalDateTime() : null);

                log.debug("user = {}, {}", user.getUserId(), user.getUserPassword());

                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuffer sql = new StringBuffer();
        sql.append(
                        "SELECT user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at ")
                .append("FROM Users ")
                .append("WHERE BINARY user_id = ?");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            statement.setString(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_birth"),
                        User.Auth.valueOf(rs.getString("user_auth")),
                        rs.getInt("user_point"),
                        Objects.nonNull(rs.getTimestamp("created_at")) ?
                                rs.getTimestamp("created_at").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("latest_login_at")) ?
                                rs.getTimestamp("latest_login_at").toLocalDateTime() : null);

                log.debug("user = {}, {}", user.getUserId(), user.getUserPassword());
                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.

        log.debug("save user birth = {}", user.getUserBirth());

        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuffer sql = new StringBuffer();
        sql.append(
                        "INSERT INTO Users(user_id, user_name, user_password, user_birth, user_auth, user_point, created_at)")
                .append(" VALUES(?, ?, ?, ?, ?, ?, ?)");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {

            psmt.setString(1, user.getUserId());
            psmt.setString(2, user.getUserName());
            psmt.setString(3, user.getUserPassword());
            psmt.setString(4, user.getUserBirth());
            psmt.setString(5, user.getUserAuth().toString());
            psmt.setInt(6, user.getUserPoint());
            psmt.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));

            log.info("save user = {}", user);
            return psmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e.getClass().getName());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.

        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM Users WHERE user_id = ?");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setString(1, userId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getClass().getName());
        }
    }

    @Override
    public int update(User user) {
        //todo#3-5 회원수정, executeUpdate()을 반환합니다.

        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE Users ")
                .append("SET user_name = ?, user_password = ?, user_birth = ?, user_auth = ?, user_point = ? ")
                .append("WHERE user_id = ?");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setString(1, user.getUserName());
            psmt.setString(2, user.getUserPassword());
            psmt.setString(3, user.getUserBirth());
            psmt.setString(4, user.getUserAuth().toString());
            psmt.setInt(5, user.getUserPoint());
            psmt.setString(6, user.getUserId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getClass().getName());
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        //todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.

        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE Users ")
                .append("SET latest_login_at = ? ")
                .append("WHERE user_id = ?");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setTimestamp(1, Timestamp.valueOf(latestLoginAt));
            psmt.setString(2, userId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getClass().getName());
        }
    }

    @Override
    public int countByUserId(String userId) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.

        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuffer sql = new StringBuffer();

        sql.append("SELECT count(*) FROM Users WHERE BINARY user_id = ?");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setString(1, userId);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {


                int result = rs.getInt(1);
                log.debug("result = {}", result);
                return result;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public Page<User> findAll(int page, int size, User.Auth auth) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        // user_id가 auto_increment -> 생성일자를 비교할 필요가 없음
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at")
                .append("FROM Users WHERE user_auth = ? ")
                .append("LIMIT ?, ?");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setString(1, auth.name());
            psmt.setInt(2, page);
            psmt.setInt(3, size);

            ResultSet rs = psmt.executeQuery();
            List<User> userList = new LinkedList<>();
            while (rs.next()) {
                userList.add(
                        new User(
                                rs.getString("user_id"),
                                rs.getString("user_name"),
                                rs.getString("user_password"),
                                rs.getString("user_birth"),
                                User.Auth.valueOf(rs.getString("user_auth")),
                                rs.getInt("user_point"),
                                rs.getTimestamp("created_at").toLocalDateTime(),
                                rs.getTimestamp("latest_login_at") == null ? null :
                                        rs.getTimestamp("latest_login_at").toLocalDateTime()
                        )
                );
            }

            return new Page<>(userList, userList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int getUserSize() {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT count(*) FROM Users WHERE user_auth = 'ROLE_USER'";

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
    public int getAdminSize() {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT count(*) FROM Users WHERE user_auth = 'ROLE_ADMIN'";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            ResultSet rs = psmt.executeQuery();

            int result = 0;
            if (rs.next()) {
                result = rs.getInt(1);
            }

            rs.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int withdraw(String userId, int totalPrice) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "UPDATE Users SET user_point = user_point - ? WHERE user_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, totalPrice);
            psmt.setString(2, userId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int updatePoint(String userId, int point) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "UPDATE Users SET user_point = ? WHERE user_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, point);
            psmt.setString(2, userId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
