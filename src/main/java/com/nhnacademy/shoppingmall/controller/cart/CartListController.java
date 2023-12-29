package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.cart.service.CartService;
import com.nhnacademy.shoppingmall.cart.service.impl.CartServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/cart.do")
public class CartListController implements BaseController {

    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);

        if (Objects.nonNull(session)) {
            User user = (User) session.getAttribute("user");
            String userId = user.getUserId();
            req.setAttribute("userId", userId);

            List<Cart> cartList = cartService.getCartListByUserId(userId);
            List<Product> productList = new ArrayList<>();

            for (Cart cart : cartList) {
                log.debug("cart = {}", cart);
                productList.add(productService.getProduct(cart.getProductId()));
            }

            for (Product product : productList) {
                log.debug("product = {}", product);
            }
            req.setAttribute("productList", productList);
            req.setAttribute("cartList", cartList);
        } else {
            log.error("session is null");
            return "redirect:/login.do";
        }

        return "shop/user/cart/list";
    }
}
