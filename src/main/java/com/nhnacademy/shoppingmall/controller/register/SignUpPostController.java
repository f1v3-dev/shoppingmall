package com.nhnacademy.shoppingmall.controller.register;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/signupAction.do")
public class SignUpPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String birth = req.getParameter("user_birth");
        birth = birth.replaceAll("-", "");
        log.debug("birth = {}", birth);

        User user = new User(
                req.getParameter("user_id"),
                req.getParameter("user_name"),
                req.getParameter("user_password"),
                birth
        );

        try {
            userService.saveUser(user);
        } catch (UserAlreadyExistsException e) {
            return "redirect:/signup.do";
        }

        return "redirect:/index.do";
    }
}
