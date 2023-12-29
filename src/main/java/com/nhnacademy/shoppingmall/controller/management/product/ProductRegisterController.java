package com.nhnacademy.shoppingmall.controller.management.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product_category.domain.ProductCategory;
import com.nhnacademy.shoppingmall.product_category.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product_category.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.product_category.service.impl.ProductCategoryServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/register.do")
public class ProductRegisterController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ProductCategoryService productCategoryService =
            new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {


        try {
            String productImage = (String) req.getAttribute("product_image");

            Product product = new Product(
                    req.getParameter("model_number"),
                    req.getParameter("model_name"),
                    productImage,
                    Integer.parseInt(req.getParameter("unit_cost")),
                    req.getParameter("description")
            );

            String[] categories = new String[3];
            for (int i = 0; i < 3; i++) {
                categories[i] = req.getParameter("category_id" + (i + 1));
            }

            productService.saveProduct(product);
            int productId = product.getProductId();

            for (String category : categories) {
                if (!category.equals("NULL")) {
                    productCategoryService.save(new ProductCategory(productId, Integer.parseInt(category)));
                }
            }

        } catch (RuntimeException e) {
            log.debug("error = {}", e.getMessage());
            return "redirect:/admin/product/register.do";
        }

        return "redirect:/admin/product.do";
    }
}