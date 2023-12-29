package com.nhnacademy.shoppingmall.category.service;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.common.page.Page;
import java.util.List;

public interface CategoryService {

    Category getCategory(int categoryId);

    void saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int categoryId);

    List<Category> getCategoryList();

    Page<Category> findAll(int page, int pageSize);

    int getCategorySize();
}
