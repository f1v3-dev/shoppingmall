package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;

public interface UserService {

    User getUser(String userId);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(String userId);

    User doLogin(String userId, String userPassword);

    Page<User> getUserList(int page, int size);

    Page<User> getAdminList(int page, int size);

    int getAdminSize();

    int getUserSize();

    void withdraw(String userId, int totalPrice);

    void updatePoint(String userId, int i);

    boolean checkDuplicate(String id);
}
