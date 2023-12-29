package com.nhnacademy.shoppingmall.controller.user.address;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/address/register.do")
public class UserAddressRegisterController implements BaseController {

    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String zipCode = req.getParameter("zip_code");
        String detailedAddress = req.getParameter("detailed_address");

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            addressService.saveAddress(new Address(user.getUserId(), zipCode, detailedAddress));

        } catch (Exception e) {
            log.error("UserAddressRegisterController = {}", e.getMessage());
        }

        return "redirect:/mypage/address.do";
    }
}
