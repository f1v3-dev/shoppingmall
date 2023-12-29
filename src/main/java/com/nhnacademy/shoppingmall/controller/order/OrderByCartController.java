package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.cart.service.CartService;
import com.nhnacademy.shoppingmall.cart.service.impl.CartServiceImpl;
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
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import com.nhnacademy.shoppingmall.thread.worker.WorkerThread;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/orderByCart.do")
public class OrderByCartController implements BaseController {

    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());
    private final OrderDetailService orderDetailService = new OrderDetailServiceImpl(new OrderDetailRepositoryImpl());
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        String userId = user.getUserId();

        int totalPrice = 0;
        List<Cart> cartList = cartService.getCartListByUserId(userId);

        for (Cart cart : cartList) {
            Product product = productService.getProduct(cart.getProductId());
            Order order = new Order(userId, LocalDateTime.now(), null);
            orderService.saveOrder(order);

            orderDetailService.saveOrderDetail(new OrderDetail(
                    order.getOrderId(),
                    product.getProductId(),
                    cart.getQuantity(),
                    product.getUnitCost())
            );

            totalPrice += (product.getUnitCost() * cart.getQuantity());
        }

        // userService.withdraw
        userService.withdraw(userId, totalPrice);

        // 주문이 완료되면 장바구니를 비운다.
        for (Cart cart : cartList) {
            cartService.deleteCart(cart.getCartId());
        }

//        session.setAttribute("user", userService.getUser(userId));

        log.debug("totalPrice = {}", totalPrice);

        try {
            ServletContext context = req.getServletContext();
            RequestChannel requestChannel = (RequestChannel) context.getAttribute("requestChannel");
            PointChannelRequest request = new PointChannelRequest(userId, totalPrice, "상품 구매 적립 (장바구니)");

            requestChannel.addRequest(request);
            WorkerThread thread = new WorkerThread(requestChannel);
            thread.start();
        } catch (InterruptedException e) {
            log.debug("적립 실패 에러 = {}", e.getMessage());
        }

        return "redirect:/mypage/order_list.do";
    }
}
