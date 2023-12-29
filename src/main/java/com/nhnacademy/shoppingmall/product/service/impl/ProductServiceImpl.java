package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(int productId) {

        Optional<Product> optionalProduct = productRepository.findByProductId(productId);
        return optionalProduct.orElse(null);
    }

    @Override
    public void saveProduct(Product product) {

        // productId는 auto Increment이므로, Model Number가 같은게 있는지 확인하는 방식을 사용함
        if (productRepository.countByModelNumber(product.getModelNumber()) > 0) {
            log.debug("save Product error");
            throw new ProductAlreadyExistsException(product.getModelName(), product.getModelNumber());
        }


        int result = productRepository.save(product);
        log.debug("result = {}", result);
        if (result < 1) {
            throw new RuntimeException();
        }
    }

    @Override
    public void updateProduct(Product product) {

        if (productRepository.countByProductId(product.getProductId()) < 1) {
            throw new ProductNotFoundException(product.getProductId());
        }

        int result = productRepository.update(product);
        if (result < 1) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteProduct(int productId) {

        if (productRepository.countByProductId(productId) < 1) {
            throw new ProductNotFoundException(productId);
        }

        int result = productRepository.deleteByProductId(productId);
        if (result < 1) {
            throw new RuntimeException();
        }
    }

    @Override
    public int getProductSize() {
        return productRepository.getProductSize();
    }

    @Override
    public int getProductSizeByCategory(int categoryId) {
        return productRepository.getProductSizeByCategory(categoryId);
    }

    @Override
    public int getSearchProductSize(String modelName) {
        return productRepository.getSearchProductSize(modelName);
    }

    @Override
    public Page<Product> findAll(int page, int pageSize) {

        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        return productRepository.findAll(offset, limit);
    }

    @Override
    public Product getProductModelNumber(String modelNumber) {

        Optional<Product> optionalProduct = productRepository.findByModelNumber(modelNumber);
        return optionalProduct.orElse(null);
    }

    @Override
    public Page<Product> findAllByModelName(String name, int page, int pageSize) {

        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        return productRepository.findAllByModelName(name, offset, limit);
    }

    @Override
    public Page<Product> findAllByCategoryId(int categoryId, int page, int pageSize) {

        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        return productRepository.findAllByCategoryId(categoryId, offset, limit);
    }
}
