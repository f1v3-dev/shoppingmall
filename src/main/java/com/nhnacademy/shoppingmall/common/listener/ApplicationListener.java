package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class ApplicationListener implements ServletContextListener {


    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.

        DbConnectionThreadLocal.initialize();

        log.info("context Initialized!");

        User admin =
                new User("admin", "admin", "12345", "19990513", User.Auth.ROLE_ADMIN, 1_000_000, LocalDateTime.now(),
                        null);


        User user = new User("user", "user", "12345", "19990513", User.Auth.ROLE_USER, 1_000_000, LocalDateTime.now(),
                null);

        if (Objects.isNull(userService.getUser(admin.getUserId()))) {
            userService.saveUser(admin);
        }

        if (Objects.isNull(userService.getUser(user.getUserId()))) {
            userService.saveUser(user);
        }

        log.info("context Initialized end----");

        DbConnectionThreadLocal.reset();

    }
}
