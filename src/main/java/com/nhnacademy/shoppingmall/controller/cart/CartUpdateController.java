package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.cart.service.CartService;
import com.nhnacademy.shoppingmall.cart.service.impl.CartServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/cart/update.do")
public class CartUpdateController implements BaseController {

    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String userId = req.getParameter("userId");
            Integer cartId = Integer.parseInt(req.getParameter("cartId"));
            Integer quantity = Integer.parseInt(req.getParameter("quantity"));
            Integer productId = Integer.parseInt(req.getParameter("productId"));
            LocalDateTime dateCreated = LocalDateTime.parse(req.getParameter("dateCreated"));

            Cart cart = new Cart(
                    cartId,
                    userId,
                    productId,
                    quantity,
                    dateCreated
            );
            cartService.updateCart(cart);
        } catch (Exception e) {
            log.error("CartUpdateController error = {}", e.getMessage());
        }
        return "redirect:/mypage/cart.do";
    }
}
