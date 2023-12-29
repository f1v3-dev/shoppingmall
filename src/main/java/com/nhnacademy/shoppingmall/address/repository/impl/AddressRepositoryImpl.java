package com.nhnacademy.shoppingmall.address.repository.impl;

import static java.sql.Statement.*;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddressRepositoryImpl implements AddressRepository {
    @Override
    public Optional<Address> findByAddressId(Integer addressId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM Address WHERE address_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setInt(1, addressId);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                Address address = new Address(
                        rs.getInt("address_id"),
                        rs.getString("user_id"),
                        rs.getString("zip_code"),
                        rs.getString("detailed_address")
                );
                return Optional.of(address);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Address> findListByUserId(String userId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM Address WHERE user_id = ?");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {

            psmt.setString(1, userId);
            ResultSet rs = psmt.executeQuery();

            List<Address> addressList = new ArrayList<>();

            while (rs.next()) {
                addressList.add(
                        new Address(
                                rs.getInt("address_id"),
                                rs.getString("user_id"),
                                rs.getString("zip_code"),
                                rs.getString("detailed_address")
                        )
                );
            }

            return addressList;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int countByUserId(String userId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT COUNT(*) FROM Address WHERE user_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

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
    public int save(Address address) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "INSERT INTO Address(user_id, zip_code, detailed_address) VALUES(?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

            psmt.setString(1, address.getUserId());
            psmt.setString(2, address.getZipCode());
            psmt.setString(3, address.getDetailedAddress());

            int result = psmt.executeUpdate();

            ResultSet generatedKeys = psmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                address.setAddressId(generatedKeys.getInt(1));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByAddressId(Integer addressId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        log.debug("addressId = {}", addressId);

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM Address WHERE address_id = ?");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setInt(1, addressId);

            int result = psmt.executeUpdate();
            log.debug("result = {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int update(Address address) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE Address ")
                .append("SET user_id = ?, zip_code = ?, detailed_address = ? ")
                .append("WHERE address_id = ?");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setString(1, address.getUserId());
            psmt.setString(2, address.getZipCode());
            psmt.setString(3, address.getDetailedAddress());
            psmt.setInt(4, address.getAddressId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int countByAddressId(Integer addressId) {

        Connection connection = DbConnectionThreadLocal.getConnection();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM Address WHERE address_id = ?");

        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            psmt.setInt(1, addressId);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return 0;
    }
}
