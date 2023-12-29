package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import java.util.List;

public interface ProductService {

    Product getProduct(int productId);

    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(int productId);

    int getProductSize();
    int getProductSizeByCategory(int categoryId);

    int getSearchProductSize(String modelName);

    Page<Product> findAll(int page, int pageSize);

    Product getProductModelNumber(String modelNumber);

    Page<Product> findAllByModelName(String name, int page, int pageSize);

    Page<Product> findAllByCategoryId(int categoryId, int page, int pageSize);

}
