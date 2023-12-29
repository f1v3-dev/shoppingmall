package com.nhnacademy.shoppingmall.address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class AddressRepositoryImplTest {

    AddressRepository addressRepository = new AddressRepositoryImpl();

    Address testAddress;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testAddress = new Address
                ("user", "12345", "광주 광역시 동구 조선대학교 IT융합대학 NHN Academy");
        addressRepository.save(testAddress);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("주소 조회 : 주소 번호로 조회")
    void findByAddressId() {
        Optional<Address> optionalAddress = addressRepository.findByAddressId(testAddress.getAddressId());
        Assertions.assertEquals(testAddress, optionalAddress.get());
    }

    @Test
    @DisplayName("주소 조회 : 유효하지 않은 주소 번호 조회 - 실패")
    void findByInvalidAddressId() {
        Optional<Address> optionalAddress = addressRepository.findByAddressId(Integer.MAX_VALUE);
        Assertions.assertEquals(Optional.empty(), optionalAddress);
    }

    @Test
    @DisplayName("주소 조회 : User ID로 조회")
    void findListByUserId() {
        List<Address> addressList = addressRepository.findListByUserId(testAddress.getUserId());
        Assertions.assertEquals(testAddress, addressList.get(0));
    }

    @Test
    @DisplayName("주소 조회 : 유효하지 않은 User ID로 조회 - 실패")
    void findListByInvalidUserId() {
        List<Address> invalidUserList = addressRepository.findListByUserId("INVALID_USER_ID");
        Assertions.assertEquals(0, invalidUserList.size());
    }

    @Test
    @DisplayName("주소 카운트 : User ID로 주소 개수 조회")
    void countByUserId() {

        String userId = testAddress.getUserId();
        int count = addressRepository.countByUserId(userId);
        Assertions.assertEquals(1, count);

        addressRepository.save(new Address(userId, "12345", "test address"));
        count = addressRepository.countByUserId(userId);
        Assertions.assertEquals(2, count);
    }

    @Test
    @DisplayName("주소 저장 : 주소 저장 성공")
    void saveAddress() {

        Address newAddress = new Address("user", "12345", "test address");
        int result = addressRepository.save(newAddress);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newAddress,
                        addressRepository.findByAddressId(newAddress.getAddressId()).get())
        );
    }

    @Test
    @DisplayName("주소 삭제 : 주소 ID로 주소 삭제")
    void deleteByAddressId() {
        int result = addressRepository.deleteByAddressId(testAddress.getAddressId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(addressRepository.findByAddressId(testAddress.getAddressId()).isPresent())
        );
    }

    @Test
    @DisplayName("주소 업데이트 : 주소 업데이트 성공")
    void updateAddress() {
        testAddress.setZipCode("58117");
        testAddress.setDetailedAddress("우리집");

        int result = addressRepository.update(testAddress);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testAddress,
                        addressRepository.findByAddressId(testAddress.getAddressId()).get())
        );
    }

}
