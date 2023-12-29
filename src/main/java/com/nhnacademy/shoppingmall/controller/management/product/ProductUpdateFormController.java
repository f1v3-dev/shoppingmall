package com.nhnacademy.shoppingmall.controller.management.product;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product_category.domain.ProductCategory;
import com.nhnacademy.shoppingmall.product_category.repository.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product_category.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product_category.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.product_category.service.impl.ProductCategoryServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/product/update.do")
public class ProductUpdateFormController implements BaseController {

    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private ProductCategoryService productCategoryService = new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        int id = Integer.parseInt(req.getParameter("id"));

        Product product = productService.getProduct(id);

        List<ProductCategory> allByProductId = productCategoryService.findAllByProductId(id);

        List<Category> categoryList = new ArrayList<>();
        for (ProductCategory productCategory : allByProductId) {
            Category category = categoryService.getCategory(productCategory.getCategoryId());
            categoryList.add(category);
        }

        req.setAttribute("categoryList", categoryService.getCategoryList());
        req.setAttribute("setCategoryList", categoryList);
        req.setAttribute("product", product);


        return "shop/admin/product/register";
    }
}
