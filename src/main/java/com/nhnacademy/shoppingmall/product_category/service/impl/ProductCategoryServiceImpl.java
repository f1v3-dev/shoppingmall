package com.nhnacademy.shoppingmall.product_category.service.impl;

import com.nhnacademy.shoppingmall.product_category.domain.ProductCategory;
import com.nhnacademy.shoppingmall.product_category.exception.ProductCategoryExceededException;
import com.nhnacademy.shoppingmall.product_category.repository.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product_category.service.ProductCategoryService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public void save(ProductCategory productCategory) {

        int result = productCategoryRepository.save(productCategory);
        if (result < 1) {
            throw new RuntimeException("ProductService save error");
        }
    }

    @Override
    public void deleteAll(int productId) {

        if (productCategoryRepository.countByProductId(productId) < 1) {
            throw new ProductCategoryExceededException("해당 상품의 카테고리가 존재하지 않습니다.");
        }

        int result = productCategoryRepository.deleteAllByProductId(productId);
        if (result < 1) {
            throw new RuntimeException("ProductService deleteAll error");
        }
    }

//    @Override
//    public void update(ProductCategory productCategory) {
//
//        int result = productCategoryRepository.update(productCategory);
//        if (result < 1) {
//            throw new RuntimeException("ProductService update error");
//        }
//    }

    @Override
    public int countByProductId(int productId) {
        return productCategoryRepository.countByProductId(productId);
    }

    @Override
    public List<ProductCategory> findAllByProductId(int productId) {
        return productCategoryRepository.findAllByProductId(productId);
    }
}
