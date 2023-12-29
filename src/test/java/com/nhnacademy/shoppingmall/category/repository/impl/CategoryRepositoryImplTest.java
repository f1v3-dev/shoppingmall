package com.nhnacademy.shoppingmall.category.repository.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class CategoryRepositoryImplTest {

    CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    Category testCategory;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testCategory = new Category("NHN-Category");
        categoryRepository.save(testCategory);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("카테고리 조회 : 카테고리 ID로 조회")
    void findByCategoryId() {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryId(testCategory.getCategoryId());
        Assertions.assertEquals(testCategory, optionalCategory.get());
    }

    @Test
    @DisplayName("카테고리 조회 : 존재하지 않는 ID로 조회")
    void findByInvalidCategoryId() {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryId(Integer.MAX_VALUE);
        Assertions.assertEquals(Optional.empty(), optionalCategory);
    }

    @Test
    @DisplayName("카테고리 등록 : save 테스트")
    void saveCategory() {
        Category newCategory = new Category("NHN-Category-2");
        int result = categoryRepository.save(newCategory);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newCategory,
                        categoryRepository.findByCategoryId(newCategory.getCategoryId()).get())
        );
    }

    @Test
    @DisplayName("카테고리 삭제 : deleteByCategoryId 테스트")
    void deleteByCategoryId() {
        int result = categoryRepository.deleteByCategoryId(testCategory.getCategoryId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(
                        categoryRepository.findByCategoryId(testCategory.getCategoryId()).isPresent())
        );
    }

    @Test
    @DisplayName("카테고리 삭제 : 존재하지 않는 ID로 삭제")
    void deleteByCategoryId_Fail() {
        int result = categoryRepository.deleteByCategoryId(Integer.MAX_VALUE);
        Assertions.assertEquals(0, result);
    }

    @Test
    @DisplayName("카테고리 수정 : update 테스트")
    void updateCategory() {
        testCategory.setCategoryName("NHN-Category-Updated");

        int result = categoryRepository.update(testCategory);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testCategory,
                        categoryRepository.findByCategoryId(testCategory.getCategoryId()).get())
        );
    }

    @Test
    @DisplayName("카테고리 개수 조회 (countById) : 카테고리 ID로 개수 조회")
    void countByCategoryId() {
        int result = categoryRepository.countByCategoryId(testCategory.getCategoryId());
        Assertions.assertEquals(1, result);
    }

    @Test
    @DisplayName("카테고리 개수 조회 (countById) : 존재하지 않는 카테고리 ID로 개수 조회 -> 0개")
    void countByInvalidCategoryId() {
        int result = categoryRepository.countByCategoryId(Integer.MAX_VALUE);
        Assertions.assertEquals(0, result);
    }

    @Test
    @DisplayName("카테고리 개수 조회 (countByName) : 카테고리명으로 개수 조회")
    void countByCategoryName() {
        int result = categoryRepository.countByCategoryName(testCategory.getCategoryName());
        Assertions.assertEquals(1, result);
    }

    @Test
    @DisplayName("카테고리 개수 조회 (countByName) : 존재하지 않는 카테고리명으로 개수 조회")
    void countByInvalidCategoryName() {
        int result = categoryRepository.countByCategoryName("INVALID_CATEGORY_NAME");
        Assertions.assertEquals(0, result);
    }

}