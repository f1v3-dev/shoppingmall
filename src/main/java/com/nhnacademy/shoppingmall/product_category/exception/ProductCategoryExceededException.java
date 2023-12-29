package com.nhnacademy.shoppingmall.product_category.exception;

public class ProductCategoryExceededException extends RuntimeException {
    public ProductCategoryExceededException(String message) {
        super(message);
    }
}
