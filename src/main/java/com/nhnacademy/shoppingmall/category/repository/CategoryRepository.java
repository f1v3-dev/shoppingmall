package com.nhnacademy.shoppingmall.category.repository;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.common.page.Page;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findByCategoryId(int categoryId);

    int save(Category category);

    int deleteByCategoryId(int categoryId);

    int update(Category category);

    int countByCategoryId(int categoryId);

    List<Category> getCategories();

    int countByCategoryName(String categoryName);

    int getCategorySize();

    Page<Category> findAll(int offset, int limit);
}
