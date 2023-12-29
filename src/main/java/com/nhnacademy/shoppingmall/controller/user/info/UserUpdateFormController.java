package com.nhnacademy.shoppingmall.controller.user.info;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/update.do")
public class UserUpdateFormController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("user");


            log.debug("user = {}", user);
            req.setAttribute("user", user);
            req.setAttribute("birth", getBirth(user.getUserBirth()));
        } catch (RuntimeException e) {
            log.debug("error = {}", e.getMessage());
            return "redirect:/index.do";
        }

        return "shop/user/update";
    }

    private String getBirth(String userBirth) {
        String year = userBirth.substring(0, 4);
        String month = userBirth.substring(4, 6);
        String day = userBirth.substring(6, 8);

        return year + "-" + month + "-" + day;
    }
}
