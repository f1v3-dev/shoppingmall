package com.nhnacademy.shoppingmall.product_category.service;

import com.nhnacademy.shoppingmall.product_category.domain.ProductCategory;
import java.util.List;

public interface ProductCategoryService {

    void save(ProductCategory productCategory);

    void deleteAll(int productId);

//    void update(ProductCategory productCategory);

    int countByProductId(int productId);

    List<ProductCategory> findAllByProductId(int productId);


}
