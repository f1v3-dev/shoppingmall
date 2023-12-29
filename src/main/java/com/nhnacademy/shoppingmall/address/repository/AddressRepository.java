package com.nhnacademy.shoppingmall.address.repository;

import com.nhnacademy.shoppingmall.address.domain.Address;
import java.util.List;
import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findByAddressId(Integer addressId);

    int save(Address address);

    int deleteByAddressId(Integer addressId);

    int update(Address address);

    int countByAddressId(Integer addressId);

    List<Address> findListByUserId(String userId);

    int countByUserId(String userId);
}
