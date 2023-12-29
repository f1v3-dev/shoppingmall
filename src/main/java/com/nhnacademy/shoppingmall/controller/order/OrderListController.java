package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.domain.OrderDescription;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/order_list.do")
public class OrderListController implements BaseController {

    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("user");

            List<OrderDescription> orderListByUserId = orderService.findOrderListByUserId(user.getUserId());
            req.setAttribute("orderList", orderListByUserId);
        } catch (Exception e) {
            log.error("order list error = {}", e.getMessage());
            return "redirect:/mypage.do";
        }

        return "shop/user/order/list";
    }
}
