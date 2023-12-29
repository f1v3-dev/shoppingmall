package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.cart.service.CartService;
import com.nhnacademy.shoppingmall.cart.service.impl.CartServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/cart/delete.do")
public class CartDeleteController implements BaseController {

    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        /**
         *                         <form method="POST" action="/mypage/cart/delete.do">
         *                             <input type="hidden" name="cartId" value="${cart.cartId}"/>
         *                             <button style="height: 100%; background-color: whitesmoke; border: 1px solid lightgray" type="submit">상품 삭제</button>
         *                         </form>
         */

        try {
            Integer cartId = Integer.parseInt(req.getParameter("cartId"));
            cartService.deleteCart(cartId);
        } catch (Exception e) {
            log.error("cart delete error = {}", e.getMessage());
        }

        return "redirect:/mypage/cart.do";
    }
}
