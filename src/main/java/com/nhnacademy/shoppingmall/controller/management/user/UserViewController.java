package com.nhnacademy.shoppingmall.controller.management.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/user/view.do")
public class UserViewController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String id = req.getParameter("id");

        try {
            User user = userService.getUser(id);

            req.setAttribute("user", user);
        } catch (Exception e) {
            log.error("회원 조회 실패");
            return "redirect:/admin/user.do";
        }

        return "shop/admin/user/view";
    }
}
