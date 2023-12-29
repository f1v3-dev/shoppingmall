package com.nhnacademy.shoppingmall.category.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.exception.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    CategoryService categoryService = new CategoryServiceImpl(categoryRepository);

    Category testCategory = new Category(9999, "NHN-Category");

    @Test
    @DisplayName("카테고리 조회 : 카테고리 ID로 조회")
    void getCart() {
        Mockito.when(categoryRepository.findByCategoryId(anyInt())).thenReturn(Optional.of(testCategory));

        Category category = categoryService.getCategory(testCategory.getCategoryId());
        Assertions.assertEquals(testCategory, category);

        Mockito.verify(categoryRepository, Mockito.times(1)).findByCategoryId(anyInt());
    }

    @Test
    @DisplayName("카테고리 조회 : 존재하지 않는 카테고리 ID일 경우 -> return null")
    void getCart_Not_Found() {
        Mockito.when(categoryRepository.findByCategoryId(anyInt())).thenReturn(Optional.empty());
        Category category = categoryService.getCategory(testCategory.getCategoryId());
        Assertions.assertNull(category);
    }

    @Test
    @DisplayName("카테고리 등록 : saveCategory")
    void saveCategory() {
        Mockito.when(categoryRepository.save(any())).thenReturn(1);
        Mockito.when(categoryRepository.save(any())).thenReturn(1);

        categoryService.saveCategory(testCategory);

        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryName(any());
        Mockito.verify(categoryRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("카테고리 등록 : 동일한 카테고리명을 등록하려는 경우")
    void saveCategory_Duplicate() {

        Mockito.when(categoryRepository.countByCategoryName(any())).thenReturn(1);
        Assertions.assertThrows(RuntimeException.class,
                () -> categoryService.saveCategory(testCategory));
    }

    @Test
    @DisplayName("카테고리 수정 : updateCategory 테스트")
    void updateCategory() {
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.update(any())).thenReturn(1);

        categoryService.updateCategory(testCategory);

        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryId(anyInt());
        Mockito.verify(categoryRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("카테고리 수정 : 존재하지 않는 카테고리일 경우 -> exception")
    void updateCategory_Exception() {
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(0);
        Assertions.assertThrows(
                CategoryNotFoundException.class, () -> categoryService.updateCategory(testCategory));
    }

    @Test
    @DisplayName("카테고리 삭제 : deleteCategory 테스트")
    void deleteCategory() {
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.deleteByCategoryId(anyInt())).thenReturn(1);

        categoryService.deleteCategory(testCategory.getCategoryId());

        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryId(anyInt());
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteByCategoryId(anyInt());
    }

    /**
     * 리스트, 페이징, 사이즈는 변동되기 때문에 테스트하기 어려워서 생략..
     */
}