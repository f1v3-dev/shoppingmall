package com.nhnacademy.shoppingmall.controller.management.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product_category.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product_category.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.product_category.service.impl.ProductCategoryServiceImpl;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/delete.do")
public class ProductDeleteController implements BaseController {

    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private ProductCategoryService productCategoryService =
            new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        int id = Integer.parseInt(req.getParameter("id"));

        if (productCategoryService.countByProductId(id) > 0) {
            productCategoryService.deleteAll(id);
        }

        // TODD : 이미지 파일 삭제
        String productImage = productService.getProduct(id).getProductImage();
        deleteImage(productImage);

        productService.deleteProduct(id);

        return "redirect:/admin/product.do";
    }

    private void deleteImage(String productImage) {
        File file = new File("/Users/seungjo/Desktop/shoppingmall/src/main/webapp/resources/images/" + productImage);
        if (file.exists()) {
            file.delete();
        }
    }
}
