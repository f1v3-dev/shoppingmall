package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.cart.service.CartService;
import com.nhnacademy.shoppingmall.cart.service.impl.CartServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/cart/add.do")
public class CartAddController implements BaseController {

    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("user");

            Cart cart = new Cart(
                    user.getUserId(),
                    Integer.parseInt(req.getParameter("productId")),
                    Integer.parseInt(req.getParameter("quantity"))
            );
            
            cartService.saveCart(cart);
        } catch (Exception e) {
            log.error("cart add error = {}", e.getMessage());

            // 상품이 이미 장바구니에 담겨있는 경우? -> index 페이지로 redirect
            return "redirect:/index.do";
        }

        return "redirect:/mypage/cart.do";
    }
}
