package com.nhnacademy.shoppingmall.product_category.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import com.nhnacademy.shoppingmall.product_category.domain.ProductCategory;
import com.nhnacademy.shoppingmall.product_category.repository.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product_category.service.ProductCategoryService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceImplTest {

    ProductCategoryRepository productCategoryRepository = Mockito.mock(ProductCategoryRepository.class);
    ProductCategoryService productCategoryService = new ProductCategoryServiceImpl(productCategoryRepository);

    ProductCategory testProductCategory = new ProductCategory(1, 1);

    @Test
    @DisplayName("상품 카테고리 저장 : save 테스트")
    void saveProductCategory() {
        Mockito.when(productCategoryRepository.save(any())).thenReturn(1);
        productCategoryService.save(testProductCategory);
        Mockito.verify(productCategoryRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("상품 카테고리 삭제 : deleteAll 테스트")
    void deleteAllProductCategory() {
        Mockito.when(productCategoryRepository.countByProductId(anyInt())).thenReturn(1);
        Mockito.when(productCategoryRepository.deleteAllByProductId(anyInt())).thenReturn(1);

        productCategoryService.deleteAll(testProductCategory.getProductId());

        Mockito.verify(productCategoryRepository, Mockito.times(1)).countByProductId(anyInt());
        Mockito.verify(productCategoryRepository, Mockito.times(1)).deleteAllByProductId(anyInt());
    }

    @Test
    @DisplayName("상품 카테고리 삭제 : 존재하지 않는 상품일 경우 -> exception 발생")
    void deleteAllProductCategory_Not_Found() {
        Mockito.when(productCategoryRepository.countByProductId(anyInt())).thenReturn(0);

        Throwable throwable = assertThrows(RuntimeException.class, () -> {
            productCategoryService.deleteAll(testProductCategory.getProductId());
        });

        log.error("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("상품 카테고리 조회 : countByProductId 테스트")
    void countByProductId() {
        Mockito.when(productCategoryRepository.countByProductId(anyInt())).thenReturn(1);
        int result = productCategoryService.countByProductId(testProductCategory.getProductId());
        assertEquals(1, result);
    }

    @Test
    @DisplayName("상품 카테고리 리스트 조회 : findAllByProductId 테스트")
    void findAllByProductId() {
        Mockito.when(productCategoryRepository.findAllByProductId(anyInt())).thenReturn(List.of(testProductCategory));
        List<ProductCategory> list =
                productCategoryService.findAllByProductId(testProductCategory.getProductId());

        Assertions.assertEquals(testProductCategory, list.get(0));
        Assertions.assertEquals(1, list.size());

        Mockito.verify(productCategoryRepository, Mockito.times(1)).findAllByProductId(anyInt());
    }
}