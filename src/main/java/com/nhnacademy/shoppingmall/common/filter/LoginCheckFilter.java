package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.user.domain.User;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(
        filterName = "loginCheckFilter",
        urlPatterns = {"/mypage.do", "/mypage/*"}
)
public class LoginCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        //todo#10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.

        HttpSession session = req.getSession(false);
        if (Objects.isNull(session)) {
            res.sendRedirect("/login.do");
        } else {
            User user = (User) session.getAttribute("user");
            if (Objects.isNull(user)) {
                res.sendRedirect("/login.do");
            } else {
                chain.doFilter(req, res);
            }
        }

    }
}