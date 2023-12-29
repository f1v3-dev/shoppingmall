package com.nhnacademy.shoppingmall.address.service.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address getAddress(Integer addressId) {

        Optional<Address> optionalAddress = addressRepository.findByAddressId(addressId);

        return optionalAddress.orElse(null);
    }

    @Override
    public List<Address> getAddressListByUserId(String userId) {

        List<Address> addressList = addressRepository.findListByUserId(userId);

        return addressList.isEmpty() ? Collections.emptyList() : addressList;
    }

    @Override
    public void saveAddress(Address address) {

        // 한 유저는 최대 3개의 주소를 가질 수 있다.
        if (addressRepository.countByUserId(address.getUserId()) >= 3) {
            throw new RuntimeException("주소는 최대 3개까지 등록 가능합니다.");
        }

        int result = addressRepository.save(address);
        if (result < 1) {
            throw new RuntimeException();
        }

    }

    @Override
    public void updateAddress(Address address) {

        if (addressRepository.countByAddressId(address.getAddressId()) < 1) {
            throw new RuntimeException();
        }

        int result = addressRepository.update(address);
        if (result < 1) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteAddress(Integer addressId) {

        if (addressRepository.countByAddressId(addressId) < 1) {
            throw new RuntimeException();
        }

        int result = addressRepository.deleteByAddressId(addressId);
        if (result < 1) {
            throw new RuntimeException();
        }

    }

    @Override
    public void deleteAllAddress(String userId) {

        List<Address> addressList = addressRepository.findListByUserId(userId);

        for (Address address : addressList) {
            int result = addressRepository.deleteByAddressId(address.getAddressId());
            if (result < 1) {
                throw new RuntimeException("delete All Address Error");
            }
        }
    }
}
