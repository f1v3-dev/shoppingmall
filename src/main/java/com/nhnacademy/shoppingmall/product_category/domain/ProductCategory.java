package com.nhnacademy.shoppingmall.product_category.domain;

import java.util.Objects;

public class ProductCategory {

    private int productId;
    private int categoryId;

    public ProductCategory(int productId, int categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductCategory that = (ProductCategory) o;
        return productId == that.productId && categoryId == that.categoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, categoryId);
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "productId=" + productId +
                ", categoryId=" + categoryId +
                '}';
    }
}
