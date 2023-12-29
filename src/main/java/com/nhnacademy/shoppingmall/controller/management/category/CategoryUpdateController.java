package com.nhnacademy.shoppingmall.controller.management.category;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/category/update.do")
public class CategoryUpdateController implements BaseController {

    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        Category category = new Category(
                Integer.parseInt(req.getParameter("category_id")),
                req.getParameter("category_name")
        );

        try {
            categoryService.updateCategory(category);
        } catch (RuntimeException e) {
            log.debug("error = {}", e.getMessage());
            return "redirect:/admin/category/update.do";
        }

        return "redirect:/admin/category.do";
    }
}
