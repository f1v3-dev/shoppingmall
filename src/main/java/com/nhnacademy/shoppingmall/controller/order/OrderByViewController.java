package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.order_detail.domain.OrderDetail;
import com.nhnacademy.shoppingmall.order_detail.repository.impl.OrderDetailRepositoryImpl;
import com.nhnacademy.shoppingmall.order_detail.service.OrderDetailService;
import com.nhnacademy.shoppingmall.order_detail.service.impl.OrderDetailServiceImpl;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import com.nhnacademy.shoppingmall.thread.worker.WorkerThread;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/orderByView.do")
public class OrderByViewController implements BaseController {

    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());
    private final OrderDetailService orderDetailService = new OrderDetailServiceImpl(new OrderDetailRepositoryImpl());

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        // product_id, session -> user
        int productId = Integer.parseInt(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        int unitCost = Integer.parseInt(req.getParameter("unitCost"));

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        Order order = new Order(user.getUserId(), LocalDateTime.now(), null);
        orderService.saveOrder(order);

        OrderDetail orderDetail = new OrderDetail(order.getOrderId(), productId, quantity, unitCost);
        orderDetailService.saveOrderDetail(orderDetail);


        int totalPrice = unitCost * quantity;
        userService.withdraw(user.getUserId(), totalPrice);

        try {
            ServletContext context = req.getServletContext();
            RequestChannel requestChannel = (RequestChannel) context.getAttribute("requestChannel");
            PointChannelRequest request =
                    new PointChannelRequest(user.getUserId(), totalPrice, "상품 구매 적립 (즉시 구매)");
            requestChannel.addRequest(request);

            WorkerThread workerThread = new WorkerThread(requestChannel);
            workerThread.start();
        } catch (InterruptedException e) {
            log.debug("적립 실패 에러 = {}", e.getMessage());
        }

        return "redirect:/mypage/order_list.do";
    }
}
