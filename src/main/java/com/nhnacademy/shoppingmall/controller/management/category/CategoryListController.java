package com.nhnacademy.shoppingmall.controller.management.category;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/category.do")
public class CategoryListController implements BaseController {

    private int currentPageNumber = 1;

    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        if (Objects.isNull(req.getParameter("page"))) {
            currentPageNumber = 1;
        } else {
            currentPageNumber = Integer.parseInt(req.getParameter("page"));
        }

        Page<Category> categoryPage = categoryService.findAll(currentPageNumber, 9);
        req.setAttribute("categoryList", categoryPage.getContent());
        req.setAttribute("currentPage", currentPageNumber);

        int size = categoryService.getCategorySize();
        req.setAttribute("totalPage", Math.ceil(size / 9.0));

        return "shop/admin/category/list";
    }
}
