package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ProductRepositoryImplTest {

    ProductRepository productRepository = new ProductRepositoryImpl();

    Product testProduct;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testProduct = new Product("NHN-TEST-1", "NHN 아카데미 후드집업", "NHN-TEST-1.png", 100_000, "NHN 아카데미 후드집업입니다.");
        productRepository.save(testProduct);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("상품 조회 : 상품 번호로 조회 성공")
    void findByProductId() {
        Optional<Product> optionalProduct = productRepository.findByProductId(testProduct.getProductId());
        Assertions.assertEquals(testProduct, optionalProduct.get());
    }

    @Test
    @DisplayName("상품 조회 : 유효하지 않은 상품 번호 조회")
    void findByInvalidProductId() {
        Optional<Product> optionalProduct = productRepository.findByProductId(Integer.MAX_VALUE);
        Assertions.assertEquals(Optional.empty(), optionalProduct);
    }

    @Test
    @DisplayName("상품 등록 : 상품 등록 성공")
    void saveProduct() {
        Product newProduct = new Product("NHN-TEST-2", "NHN 아카데미 티셔츠", "NHN-TEST-2.png", 100_000, "NHN 아카데미 티셔츠입니다.");

        int result = productRepository.save(newProduct);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newProduct, productRepository.findByProductId(newProduct.getProductId()).get()));
    }

    @Test
    @DisplayName("상품 등록 : 상품 등록 실패(중복 상품)")
    void saveDuplicateProduct() {

        Throwable throwable =
                Assertions.assertThrows(RuntimeException.class, () -> productRepository.save(testProduct));

        Assertions.assertTrue(
                throwable.getMessage().contains(SQLIntegrityConstraintViolationException.class.getName()));

        log.debug("error message = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("상품 삭제 : 상품 삭제 성공")
    void deleteProduct() {
        int result = productRepository.deleteByProductId(testProduct.getProductId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(productRepository.findByProductId(testProduct.getProductId()).isPresent()));
    }

    @Test
    @DisplayName("상품 삭제 : 존재하지 않는 상품 삭제 실패")
    void deleteInvalidProduct() {
        int result = productRepository.deleteByProductId(Integer.MAX_VALUE);
        Assertions.assertEquals(0, result);
    }

    @Test
    @DisplayName("상품 수정 : 상품 수정 성공")
    void updateProduct() {
        testProduct.setModelName("NHN 아카데미 후드집업 수정");
        testProduct.setModelNumber("NHN-TEST-2");
        testProduct.setUnitCost(200_000);
        testProduct.setDescription("NHN 아카데미 후드집업 수정입니다.");
        testProduct.setProductImage("NHN-TEST-2.png");

        int result = productRepository.update(testProduct);
        log.debug("update product = {}", productRepository.findByProductId(testProduct.getProductId()).get());
        Assertions.assertAll(() -> Assertions.assertEquals(1, result), () -> Assertions.assertEquals(testProduct,
                productRepository.findByProductId(testProduct.getProductId()).get()));
    }

    @Test
    @DisplayName("상품 개수 조회(ProductId) : 테스트 상품 ID로 조회")
    void countByProductId_ValidProduct() {
        int result = productRepository.countByProductId(testProduct.getProductId());
        Assertions.assertEquals(1, result);
    }

    @Test
    @DisplayName("상품 개수 조회(ProductId) : 존재하지 않는 상품 조회")
    void countByProductId_InvalidProduct() {
        int result = productRepository.countByProductId(9999);
        Assertions.assertEquals(0, result);
    }

    @Test
    @DisplayName("상품 개수 조회(ModelNumber) : 테스트 상품 모델 번호로 조회")
    void countByModelNumber_ValidProduct() {
        int result = productRepository.countByModelNumber(testProduct.getModelNumber());
        Assertions.assertEquals(1, result);
    }

    @Test
    @DisplayName("상품 개수 조회(ModelNumber) : 존재하지 않는 상품 조회")
    void countByModelNumber_InvalidProduct() {
        int result = productRepository.countByModelNumber("NHN-TEST-2");
        Assertions.assertEquals(0, result);
    }

    @Test
    @Disabled
    @DisplayName("상품 테이블 사이즈 조회 - Test DB가 아니기 떄문에 테스트 불가")
    void product_Table_TotalSize() {

        int result = productRepository.getProductSize();
        Assertions.assertEquals(14, result);

        Product newProduct = new Product("NHN-TEST-2", "NHN 아카데미 티셔츠", "NHN-TEST-2.png", 100_000, "NHN 아카데미 티셔츠입니다.");
        productRepository.save(newProduct);

        result = productRepository.getProductSize();
        Assertions.assertEquals(15, result);
    }

    @Test
    @Disabled
    @DisplayName("상품 페이징 처리 - Test DB가 아니기 때문에 테스트하기 애매함.. ")
    void product_findAll() {
        // 얜 또 어떻게 해???
        Page<Product> firstPage = productRepository.findAll(1, 10);
        Assertions.assertEquals(10, firstPage.getTotalCount());
        Assertions.assertEquals(10, firstPage.getContent().size());
    }

    @Test
    @DisplayName("상품 조회 : 상품 모델 번호로 조회 성공")
    void findByModelNumber_ValidProduct() {
        Optional<Product> optionalProduct = productRepository.findByModelNumber(testProduct.getModelNumber());
        Assertions.assertEquals(testProduct, optionalProduct.get());
    }

    @Test
    @DisplayName("상품 조회 : 존재하지 않는 상품 모델 번호 조회")
    void findByModelNumber_InvalidProduct() {
        Optional<Product> optionalProduct = productRepository.findByModelNumber("NHN-TEST-2");
        Assertions.assertEquals(Optional.empty(), optionalProduct);
    }
}