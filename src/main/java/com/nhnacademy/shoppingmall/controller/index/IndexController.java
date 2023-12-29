package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product_category.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product_category.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.product_category.service.impl.ProductCategoryServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = {"/index.do"})
public class IndexController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private final ProductCategoryService productCategoryService =
            new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());

    private int currentPageNumber = 1;


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        getCurrentPageNumber(req);

        if (Objects.isNull(req.getParameter("categoryId"))) {
            Page<Product> productPage = productService.findAll(currentPageNumber, 9);
            req.setAttribute("productList", productPage.getContent());

            int size = productService.getProductSize();
            req.setAttribute("totalPage", Math.ceil(size / 9.0));
        } else {
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            Page<Product> productPage = productService.findAllByCategoryId(categoryId, currentPageNumber, 9);
            req.setAttribute("productList", productPage.getContent());
            req.setAttribute("categoryId", categoryId);

            int size = productService.getProductSizeByCategory(categoryId);
            req.setAttribute("totalPage", Math.ceil(size / 9.0));
        }

        req.setAttribute("currentPage", currentPageNumber);

        List<Product> recentViewList = getRecentViewList(req, resp);
        req.setAttribute("recentViewList", recentViewList);

        req.setAttribute("categoryList", categoryService.getCategoryList());

        return "shop/main/index";
    }

    private void getCurrentPageNumber(HttpServletRequest req) {
        if (Objects.isNull(req.getParameter("page"))) {
            currentPageNumber = 1;
        } else {
            currentPageNumber = Integer.parseInt(req.getParameter("page"));
        }
    }

    private List<Product> getRecentViewList(HttpServletRequest req, HttpServletResponse resp) {

        Cookie[] cookies = req.getCookies();
        if (Objects.isNull(cookies)) {
            return Collections.emptyList();
        }

        List<Product> recentViewList = new ArrayList<>();

        // cookie의 value를 기준으로 내림차순 정렬
        Arrays.sort(cookies, Comparator.comparing(Cookie::getValue).reversed());

        int count = 0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains("product_")) {
                String[] split = cookie.getName().split("_");
                int productId = Integer.parseInt(split[1]);

                if (count < 5) {
                    Product product = Objects.requireNonNull(productService.getProduct(productId));
                    recentViewList.remove(product);
                    recentViewList.add(product);
                } else {
                    log.debug("cookie = {}", cookie.getName());
                    cookie.setHttpOnly(true);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setComment("최근 본 상품");
                    resp.addCookie(cookie);
                }

                count++;
            }
        }

        return recentViewList;
    }
}