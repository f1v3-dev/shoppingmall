package com.nhnacademy.shoppingmall.address.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

    AddressRepository addressRepository = Mockito.mock(AddressRepository.class);
    AddressService addressService = new AddressServiceImpl(addressRepository);

    Address testAddress = new Address(9999, "NHN_TEST_USER", "12345", "NHN Academy 광주 캠퍼스");


    @Test
    @DisplayName("주소 조회(AddressId) : getAddress")
    void getAddress() {
        Mockito.when(addressRepository.findByAddressId(anyInt())).thenReturn(Optional.of(testAddress));
        addressService.getAddress(testAddress.getAddressId());
        Mockito.verify(addressRepository, Mockito.times(1)).findByAddressId(anyInt());
    }

    @Test
    @DisplayName("주소 조회(AddressId) : 존재하지 않는 주소일 경우 -> return null")
    void getAddress_Not_Found() {
        Mockito.when(addressRepository.findByAddressId(anyInt())).thenReturn(Optional.empty());
        Address address = addressService.getAddress(testAddress.getAddressId());
        Assertions.assertNull(address);
    }


    @Test
    @DisplayName("주소 리스트 조회 : 유저 ID로 조회")
    void getAddressListByUserId() {
        Mockito.when(addressRepository.findListByUserId(anyString())).thenReturn(List.of(testAddress));
        List<Address> addressList = addressService.getAddressListByUserId(testAddress.getUserId());
        Assertions.assertAll(

                () -> Assertions.assertEquals(testAddress, addressList.get(0)),
                () -> Assertions.assertEquals(1, addressList.size()));
    }

    @Test
    @DisplayName("주소 리스트 조회 : 존재하지 않는 유저일 경우 -> return Collections.emptyList()")
    void getAddressList_ByInvalidUserId_Empty() {
        Mockito.when(addressRepository.findListByUserId(anyString())).thenReturn(List.of());
        List<Address> addressList = addressService.getAddressListByUserId(testAddress.getUserId());
        Assertions.assertAll(() -> Assertions.assertEquals(Collections.emptyList(), addressList),
                () -> Assertions.assertEquals(0, addressList.size()));
    }

    @Test
    @DisplayName("주소 저장 : saveAddress")
    void saveAddress() {
        Mockito.when(addressRepository.countByAddressId(anyInt())).thenReturn(0);
        Mockito.when(addressRepository.save(any())).thenReturn(1);

        Address newAddress = new Address("user", "12345", "test address");
        addressService.saveAddress(newAddress);
        Mockito.verify(addressRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("주소 저장 : 한 회원이 3개 이상의 주소를 가질 경우")
    void save_OverThree_Address() {
        Mockito.when(addressRepository.countByUserId(anyString())).thenReturn(3);
        Throwable throwable =
                Assertions.assertThrows(RuntimeException.class, () -> addressService.saveAddress(testAddress));

        log.debug("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("주소 업데이트 : updateAddress")
    void updateAddress() {
        Mockito.when(addressRepository.countByAddressId(anyInt())).thenReturn(1);
        Mockito.when(addressRepository.update(any())).thenReturn(1);

        testAddress.setAddressId(58117);
        testAddress.setDetailedAddress("우리집");
        addressService.updateAddress(testAddress);

        Mockito.verify(addressRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("주소 업데이트 : 존재하지 않는 주소일 경우")
    void update_Invalid_Address() {
        Mockito.when(addressRepository.countByAddressId(anyInt())).thenReturn(0);
        Throwable throwable =
                Assertions.assertThrows(RuntimeException.class, () -> addressService.updateAddress(testAddress));

        log.debug("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("주소 삭제 : deleteAddress")
    void deleteAddress() {
        Mockito.when(addressRepository.countByAddressId(anyInt())).thenReturn(1);
        Mockito.when(addressRepository.deleteByAddressId(anyInt())).thenReturn(1);

        addressService.deleteAddress(testAddress.getAddressId());

        Mockito.verify(addressRepository, Mockito.times(1)).deleteByAddressId(anyInt());
    }

    @Test
    @DisplayName("주소 삭제 : 존재하지 않는 주소 ID일 경우")
    void delete_Invalid_Address() {
        Mockito.when(addressRepository.countByAddressId(anyInt())).thenReturn(0);

        Throwable throwable = Assertions.assertThrows(RuntimeException.class,
                () -> addressService.deleteAddress(testAddress.getAddressId()));

        log.debug("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("주소 삭제 : deleteAllAddress")
    void deleteAllAddress() {
        Mockito.when(addressRepository.findListByUserId(anyString())).thenReturn(List.of(testAddress));
        Mockito.when(addressRepository.deleteByAddressId(anyInt())).thenReturn(1);

        addressService.deleteAllAddress(testAddress.getUserId());

        Mockito.verify(addressRepository, Mockito.times(1)).deleteByAddressId(anyInt());
    }
}
