package com.nhnacademy.shoppingmall.address.service;

import com.nhnacademy.shoppingmall.address.domain.Address;
import java.util.List;

public interface AddressService {

    Address getAddress(Integer addressId);

    List<Address> getAddressListByUserId(String userId);

    void saveAddress(Address address);

    void updateAddress(Address address);

    void deleteAddress(Integer addressId);

    void deleteAllAddress(String userId);
}
