package com.nhnacademy.shoppingmall.product.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductService productService = new ProductServiceImpl(productRepository);

    Product testProduct =
            new Product(9999, "NHN-TEST-1", "NHN 아카데미 후드집업", "NHN-TEST-1.png", 100_000,
                    "NHN 아카데미 후드집업입니다.");


    @Test
    @DisplayName("상품 조회(ProductId) : getProduct")
    void getProduct() {
        Mockito.when(productRepository.findByProductId(anyInt())).thenReturn(Optional.of(testProduct));

        Product product = productService.getProduct(testProduct.getProductId());
        Assertions.assertEquals(testProduct, product);

        Mockito.verify(productRepository, Mockito.times(1)).findByProductId(anyInt());
    }

    @Test
    @DisplayName("상품 조회(ProductId) : 존재하지 않는 상품일 경우 -> return null")
    void getProduct_Not_Found() {
        Mockito.when(productRepository.findByProductId(anyInt())).thenReturn(Optional.empty());
        Product product = productService.getProduct(testProduct.getProductId());
        Assertions.assertNull(product);
    }

    @Test
    @DisplayName("상품 등록 : saveProduct 테스트")
    void saveProductTest() {
        Mockito.when(productRepository.countByModelNumber(anyString())).thenReturn(0);
        Mockito.when(productRepository.save(any())).thenReturn(1);
        productService.saveProduct(testProduct);
        Mockito.verify(productRepository, Mockito.times(1)).countByModelNumber(anyString());
        Mockito.verify(productRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("상품 등록 : 이미 존재하는 모델번호일 경우 -> exception")
    void saveProduct_Exists() {
        Mockito.when(productRepository.countByModelNumber(anyString())).thenReturn(1);
        Throwable throwable =
                Assertions.assertThrows(ProductAlreadyExistsException.class,
                        () -> productService.saveProduct(testProduct));
        log.debug("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("상품 업데이트 : updateProduct")
    void updateProductTest() {
        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(1);
        Mockito.when(productRepository.update(testProduct)).thenReturn(1);

        productService.updateProduct(testProduct);

        Mockito.verify(productRepository, Mockito.times(1)).countByProductId(anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("상품 업데이트 : 존재하지 않는 상품일 경우 -> exception")
    void updateProduct_Not_Exists() {
        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(0);

        Throwable throwable =
                Assertions.assertThrows(ProductNotFoundException.class,
                        () -> productService.updateProduct(testProduct));
        log.debug("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("상품 삭제 : deleteProduct")
    void deleteProduct() {
        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(1);
        Mockito.when(productRepository.deleteByProductId(anyInt())).thenReturn(1);

        productService.deleteProduct(testProduct.getProductId());

        Mockito.verify(productRepository, Mockito.times(1)).countByProductId(anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).deleteByProductId(anyInt());
    }

    @Test
    @DisplayName("상품 삭제 : 존재하지 않는 상품일 경우 -> exception")
    void deleteProduct_Not_Exists() {
        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(0);

        Throwable throwable =
                Assertions.assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(
                        testProduct.getProductId()));

        log.debug("error = {}", throwable.getMessage());
    }

    @Test
    @DisplayName("상품 총 사이즈 : getProductSize Test")
    void getProductSizeTest() {
        Mockito.when(productRepository.getProductSize()).thenReturn(10);
        Assertions.assertEquals(10, productService.getProductSize());
    }

    @Test
    @Disabled
    @DisplayName("상품 페이징 테스트 : findAll")
    void findAllTest() {
//        Mockito.when(productRepository.findAll(anyInt(), anyInt())).thenReturn(new Page<>()) -> ??
    }

    @Test
    @DisplayName("상품 조회(Product ModelNumber) : 모델 번호를 통해 조회")
    void getProduct_ByModelNumber() {
        Mockito.when(productRepository.findByModelNumber(anyString())).thenReturn(Optional.of(testProduct));

        Product product = productService.getProductModelNumber(testProduct.getModelNumber());
        Assertions.assertEquals(product, testProduct);

        Mockito.verify(productRepository, Mockito.times(1)).findByModelNumber(anyString());
    }

    @Test
    @DisplayName("상품 조회(Product ModelNumber) : 존재하지 않는 상품일 경우 -> return null")
    void getProduct_ByModelNumber_Not_Found() {
        Mockito.when(productRepository.findByModelNumber(anyString())).thenReturn(Optional.empty());
        Product product = productService.getProductModelNumber(testProduct.getModelNumber());
        Assertions.assertNull(product);
    }
}