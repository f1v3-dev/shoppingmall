package com.nhnacademy.shoppingmall.product.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String modelName, String modelNumber) {
        super("Product already exists: " + modelName + " (" + modelNumber + ")");
    }
}
