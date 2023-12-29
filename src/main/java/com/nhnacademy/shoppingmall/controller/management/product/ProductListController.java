package com.nhnacademy.shoppingmall.controller.management.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/product.do")
public class ProductListController implements BaseController {

    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    private int currentPageNumber = 1;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {


        // 1. 상품 목록을 Page<Product> 형태로 받아온다.

        // 2. 한 페이지에 9개씩 상품을 보여주는 방식을 사용할 것임
        // Query String으로 page 번호를 받아 해당 페이지에 해당하는 상품 목록을 보여줌

        if (Objects.isNull(req.getParameter("page"))) {
            currentPageNumber = 1;
        } else {
            currentPageNumber = Integer.parseInt(req.getParameter("page"));
        }

        Page<Product> productPage = productService.findAll(currentPageNumber, 10);

        // 3. 상품 리스트를 request에 담아서 보냄

        req.setAttribute("productList", productPage.getContent());
        req.setAttribute("currentPage", currentPageNumber);

        // 4. 총 보여줘야되는 페이지의 수를 request에 담아서 보냄
        int size = productService.getProductSize();
        req.setAttribute("totalPage", Math.ceil(size / 10.0));

        return "shop/admin/product/list";
    }
}