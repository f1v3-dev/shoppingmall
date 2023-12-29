package com.nhnacademy.shoppingmall.controller.management.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/user/role_user.do")
public class UserListController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private int currentPageNumber = 1;
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (Objects.nonNull(req.getParameter("page"))) {
            currentPageNumber = Integer.parseInt(req.getParameter("page"));
        }

        Page<User> userList = userService.getUserList(currentPageNumber, 10);

        log.debug("userList = {}", userList.getContent());
        req.setAttribute("userList", userList.getContent());

        int size = userService.getUserSize();
        req.setAttribute("page", Math.ceil(size / 10.0));

        req.setAttribute("role", "role_user");

        return "shop/admin/user/list";
    }
}
