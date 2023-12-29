package com.nhnacademy.shoppingmall.category.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(int categoryId) {
        super(String.format("Category already exists : %d", categoryId));
    }

    public CategoryAlreadyExistsException(String categoryName) {
        super(String.format("Category already exists : %s", categoryName));
    }
}