package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
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
@RequestMapping(method = RequestMapping.Method.GET, value = "/product/search.do")
public class ProductSearchController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    private int currentPageNumber;
    private String searchName;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        searchName = req.getParameter("name");
        log.debug("Model name = {}", searchName);

        if (Objects.nonNull(req.getParameter("page"))) {
            currentPageNumber = Integer.parseInt(req.getParameter("page"));
        } else {
            currentPageNumber = 1;
        }

        req.setAttribute("searchName", searchName);

        Page<Product> productPage = productService.findAllByModelName(searchName, currentPageNumber, 9);
        req.setAttribute("productList", productPage.getContent());


        int size = productService.getSearchProductSize(searchName);
        log.debug("size = {}", size);

        req.setAttribute("totalPage", Math.ceil(size / 9.0));
        req.setAttribute("currentPage", currentPageNumber);

        log.debug("totalPage = {}, currentPage = {}", Math.ceil(size / 9.0), currentPageNumber);

        List<Product> recentViewList = getRecentViewList(req, resp);
        req.setAttribute("recentViewList", recentViewList);
        return "shop/product/search";
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
