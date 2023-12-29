package com.nhnacademy.shoppingmall.category.service.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.exception.CategoryAlreadyExistsException;
import com.nhnacademy.shoppingmall.category.exception.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategory(int categoryId) {

        Optional<Category> optionalCategory = categoryRepository.findByCategoryId(categoryId);

        return optionalCategory.orElse(null);
    }

    @Override
    public void saveCategory(Category category) {
        if (categoryRepository.countByCategoryName(category.getCategoryName()) > 0) {
            throw new CategoryAlreadyExistsException(category.getCategoryName());
        }

        int result = categoryRepository.save(category);
        if (result < 1) {
            throw new RuntimeException();
        }
    }

    @Override
    public void updateCategory(Category category) {

        if (categoryRepository.countByCategoryId(category.getCategoryId()) < 1) {
            throw new CategoryNotFoundException(category.getCategoryId());
        }

        int result = categoryRepository.update(category);
        if (result < 1) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteCategory(int categoryId) {

        if (categoryRepository.countByCategoryId(categoryId) < 1) {
            throw new CategoryNotFoundException(categoryId);
        }

        int result = categoryRepository.deleteByCategoryId(categoryId);
        if (result < 1) {
            throw new RuntimeException();
        }

    }

    @Override
    public List<Category> getCategoryList() {

        return categoryRepository.getCategories();
    }

    @Override
    public Page<Category> findAll(int page, int pageSize) {

        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        return categoryRepository.findAll(offset, limit);
    }

    @Override
    public int getCategorySize() {

        return categoryRepository.getCategorySize();
    }
}
