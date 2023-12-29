package com.nhnacademy.shoppingmall.product_category.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product_category.domain.ProductCategory;
import com.nhnacademy.shoppingmall.product_category.repository.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class ProductCategoryRepositoryImplTest {

    ProductCategoryRepository productCategoryRepository = new ProductCategoryRepositoryImpl();

    ProductCategory testProductCategory;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();

        // 이미 존재하는 상품으로 테스트를 하다보니 기존에 2개의 카테고리가 있음
        testProductCategory = new ProductCategory(1, 1);
        productCategoryRepository.save(testProductCategory);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("상품 카테고리 저장 : save 테스트")
    void saveProductCategory() {

        // 1번은 이미 3개의 카테고리가 존재하여 2번으로 테스트하였습니다.

        ProductCategory newProductCategory = new ProductCategory(2, 3);
        int result = productCategoryRepository.save(newProductCategory);
        Assertions.assertEquals(1, result);
    }

    @Test
    @DisplayName("상품 카테고리 저장 : 한 개의 상품이 3개 이상의 카테고리 -> excpetion 발생")
    void Invalid_saveProductCategory() {
        ProductCategory addCategory = new ProductCategory(1, 3);

        log.error("{}", productCategoryRepository.countByProductId(1));
        Throwable throwable = Assertions.assertThrows(RuntimeException.class, () -> {
            productCategoryRepository.save(addCategory);
        });

        log.error("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("상품 카테고리 삭제 : delete 테스트")
    void deleteAllByProductId() {
        int result = productCategoryRepository.deleteAllByProductId(testProductCategory.getProductId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(3, result),
                () -> Assertions.assertEquals(0,
                        productCategoryRepository.countByProductId(testProductCategory.getProductId()))
        );
    }

    @Test
    @DisplayName("상품 카테고리 삭제 : 존재하지 않는 상품번호로 삭제")
    void deleteAllByInvalidProductId() {
        Throwable throwable
                = Assertions.assertThrows(RuntimeException.class, () -> {
            productCategoryRepository.deleteAllByProductId(Integer.MAX_VALUE);
        });

        log.error("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("상품 카테고리 수 조회 : countByProductId 테스트")
    void countByProductId() {
        int result = productCategoryRepository.countByProductId(testProductCategory.getProductId());
        Assertions.assertEquals(3, result);
    }

}