package com.nhnacademy.shoppingmall.controller.register;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RequestMapping(method = RequestMapping.Method.GET, value = "/signup.do")
public class SignUpController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if (Objects.nonNull(user)) {
            return "redirect:/index.do";
        }

        return "shop/signup/sign_up_form";
    }
}
