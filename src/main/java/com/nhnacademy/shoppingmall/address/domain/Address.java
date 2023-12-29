package com.nhnacademy.shoppingmall.address.domain;

import java.util.Objects;

public class Address {

    private Integer addressId;
    private String userId;
    private String zipCode;
    private String detailedAddress;

    public Address(String userId, String zipCode, String detailedAddress) {
        this.addressId = null;
        this.userId = userId;
        this.zipCode = zipCode;
        this.detailedAddress = detailedAddress;
    }

    public Address(Integer addressId, String userId, String zipCode, String detailedAddress) {
        this.addressId = addressId;
        this.userId = userId;
        this.zipCode = zipCode;
        this.detailedAddress = detailedAddress;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public String getUserId() {
        return userId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(addressId, address.addressId) && Objects.equals(userId, address.userId) &&
                Objects.equals(zipCode, address.zipCode) &&
                Objects.equals(detailedAddress, address.detailedAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, userId, zipCode, detailedAddress);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", userId='" + userId + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", detailedAddress='" + detailedAddress + '\'' +
                '}';
    }
}
