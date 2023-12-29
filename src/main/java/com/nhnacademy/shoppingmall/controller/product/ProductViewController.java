package com.nhnacademy.shoppingmall.controller.product;

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
import java.sql.Timestamp;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/product/view.do")
public class ProductViewController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        int id = Integer.parseInt(req.getParameter("id"));

        try {
            Product product = Objects.requireNonNull(productService.getProduct(id));

            req.setAttribute("product", product);

            // key -> product_productId
            // value -> TIMESTAMP로 현재시간 찍어주기
            Timestamp time = new Timestamp(System.currentTimeMillis());
            Cookie cookie = new Cookie(("product_" + product.getProductId()), String.valueOf(time.getTime()));

            // 자바스크립트에서 쿠키를 접근하게 못한다.
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60);
            cookie.setPath("/");
            cookie.setComment("최근 본 상품");

            resp.addCookie(cookie);

        } catch (NullPointerException e) {
            return "redirect:/index.do";
        }


        return "shop/product/view";
    }
}
