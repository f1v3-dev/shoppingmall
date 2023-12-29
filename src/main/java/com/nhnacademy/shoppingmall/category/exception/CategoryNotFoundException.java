package com.nhnacademy.shoppingmall.category.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(int categoryId) {
        super(String.format("category not found : %d", categoryId));
    }
}