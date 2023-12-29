package com.nhnacademy.shoppingmall.product_category.repository;

import com.nhnacademy.shoppingmall.product_category.domain.ProductCategory;
import java.util.List;

public interface ProductCategoryRepository {
    int save(ProductCategory productCategory);

    int deleteAllByProductId(int productId);


//    int update(ProductCategory productCategory);

    int countByProductId(int productId);

    List<ProductCategory> findAllByProductId(int productId);
}
