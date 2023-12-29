package com.nhnacademy.shoppingmall.controller.management.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/user.do")
public class UserManageController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        return "/shop/admin/user/user_manage";
    }
}
