package com.nhnacademy.shoppingmall.controller.user.point;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.point_history.domain.PointHistory;
import com.nhnacademy.shoppingmall.point_history.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point_history.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point_history.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/point.do")
public class PointViewController implements BaseController {

    private final PointHistoryService pointHistoryService = new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        List<PointHistory> pointHistories = pointHistoryService.getPointHistories(user.getUserId());

        log.debug("pointHistories = {}", pointHistories);

        req.setAttribute("pointHistories", pointHistories);

        return "shop/user/point/list";
    }
}
