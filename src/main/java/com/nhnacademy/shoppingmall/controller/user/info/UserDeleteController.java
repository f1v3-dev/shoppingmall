package com.nhnacademy.shoppingmall.controller.user.info;

import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/deleteUser.do")
public class UserDeleteController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("user");

            addressService.deleteAllAddress(user.getUserId());
            userService.deleteUser(user.getUserId());
        } catch (Exception e) {
            log.error("UserDeleteController = {}", e.getMessage());
        }

        req.getSession(false).invalidate();

        return "redirect:/index.do";
    }
}
