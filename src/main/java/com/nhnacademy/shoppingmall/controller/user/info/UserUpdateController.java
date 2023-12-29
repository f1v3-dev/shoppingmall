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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/update.do")
public class UserUpdateController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String birth = req.getParameter("user_birth");
        birth = birth.replaceAll("-", "");

        User user = new User(
                req.getParameter("user_id"),
                req.getParameter("user_name"),
                req.getParameter("user_password"),
                birth
        );

        log.debug("user = {}", user);


        try {
            userService.updateUser(user);
        } catch (RuntimeException e) {
            log.debug("error = {}", e.getMessage());
            return "redirect:/mypage.do?id=" + user.getUserId();
        }

        return "redirect:/index.do";
    }
}
