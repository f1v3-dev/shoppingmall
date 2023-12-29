package com.nhnacademy.shoppingmall.category.domain;

import java.util.Objects;

public class Category {

    /**
     * CREATE TABLE Categories
     * (
     *     category_id   INT auto_increment,
     *     category_name varchar(50),
     *     CONSTRAINT pk_Categories PRIMARY KEY (category_id)
     * ) ENGINE = InnoDB
     *   DEFAULT CHARSET = utf8mb4
     *   COLLATE = utf8mb4_0900_ai_ci COMMENT ='카테고리';
     */

    private Integer categoryId;
    private String categoryName;

    public Category(String categoryName) {
        this.categoryId = null;
        this.categoryName = categoryName;
    }

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return categoryId == category.categoryId && Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
