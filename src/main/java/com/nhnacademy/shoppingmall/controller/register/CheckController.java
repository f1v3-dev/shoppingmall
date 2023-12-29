package com.nhnacademy.shoppingmall.controller.register;

import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    public boolean checkDuplicate(String id) {
        return userService.checkDuplicate(id);
    }
}
