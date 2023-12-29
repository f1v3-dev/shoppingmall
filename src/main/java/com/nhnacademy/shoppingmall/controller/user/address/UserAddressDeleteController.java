package com.nhnacademy.shoppingmall.controller.user.address;

import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/address/delete.do")
public class UserAddressDeleteController implements BaseController {

    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        int addressId = Integer.parseInt(req.getParameter("id"));

        try {
            addressService.deleteAddress(addressId);
        } catch (Exception e) {
            log.error("UserAddressDeleteController = {}", e.getMessage());
        }

        return "redirect:/mypage/address.do";
    }
}
