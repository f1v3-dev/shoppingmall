package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findByProductId(int productId);


    int save(Product product);

    int deleteByProductId(int productId);

    int update(Product product);

    int countByProductId(int productId);

    int countByModelNumber(String modelNumber);

    int getProductSize();

    int getProductSizeByCategory(int categoryId);


    int getSearchProductSize(String modelName);


    Page<Product> findAll(int offset, int limit);

    Optional<Product> findByModelNumber(String modelNumber);

    Page<Product> findAllByModelName(String name, int offset, int limit);

    Page<Product> findAllByCategoryId(int categoryId, int offset, int limit);

}
