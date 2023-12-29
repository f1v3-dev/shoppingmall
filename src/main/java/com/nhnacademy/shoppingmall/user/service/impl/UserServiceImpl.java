package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point_history.domain.PointHistory;
import com.nhnacademy.shoppingmall.point_history.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point_history.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point_history.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotEnoughPointException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final PointHistoryService pointHistoryService =
            new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());

    @Override
    public User getUser(String userId) {
        //todo#4-1 회원조회

        Optional<User> optionalUser = userRepository.findById(userId);

        return optionalUser.orElse(null);
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록

        if (userRepository.countByUserId(user.getUserId()) > 0) {
            throw new UserAlreadyExistsException(user.getUserId());
        }

        int result = userRepository.save(user);
        if (result < 1) {
            throw new RuntimeException("등록 실패");
        }
    }

    @Override
    public void updateUser(User user) {
        //todo#4-3 회원수정

        if (userRepository.countByUserId(user.getUserId()) < 1) {
            throw new UserNotFoundException(user.getUserId());
        }

        int result = userRepository.update(user);
        if (result < 1) {
            throw new RuntimeException("수정 실패");
        }
    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제

        if (userRepository.countByUserId((userId)) < 1) {
            throw new UserNotFoundException(userId);
        }

        log.debug("userId = {}", userId);
        int result = userRepository.deleteByUserId(userId);
        if (result < 1) {
            throw new RuntimeException("삭제 실패");
        }
    }

    @Override
    public User doLogin(String userId, String userPassword) {

        Optional<User> optionalUser = userRepository.findByUserIdAndUserPassword(userId, userPassword);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            LocalDate now = LocalDate.now();
            if (user.getLatestLoginAt() == null || now.isBefore(user.getLatestLoginAt().toLocalDate())) {
                updatePoint(user.getUserId(), 10_000);

                pointHistoryService.savePointHistory(
                        new PointHistory(user.getUserId(), 10_000, "로그인 포인트 적립", LocalDateTime.now()));
            }

            userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
            return user;
        }
        throw new UserNotFoundException(userId);
    }

    @Override
    public Page<User> getUserList(int page, int pageSize) {

        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        return userRepository.findAll(offset, limit, User.Auth.ROLE_USER);
    }

    @Override
    public Page<User> getAdminList(int page, int pageSize) {

        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        return userRepository.findAll(offset, limit, User.Auth.ROLE_ADMIN);
    }

    @Override
    public int getAdminSize() {
        return userRepository.getAdminSize();
    }

    @Override
    public int getUserSize() {
        return userRepository.getUserSize();
    }

    @Override
    public void withdraw(String userId, int totalPrice) {

        // 남아있는 포인트보다 더 많은 포인트를 사용하려고 하면 exception
        User user = getUser(userId);
        if (user.getUserPoint() < totalPrice) {
            throw new UserNotEnoughPointException(userId);
        }

        int result = userRepository.withdraw(userId, totalPrice);
        if (result < 1) {
            throw new RuntimeException("포인트 차감 실패");
        }
    }

    @Override
    public void updatePoint(String userId, int point) {
        int result = userRepository.updatePoint(userId, point);
        if (result < 1) {
            throw new RuntimeException("포인트 적립 실패");
        }
    }

    @Override
    public boolean checkDuplicate(String id) {

        Optional<User> optionalUser = userRepository.findById(id);
        return !optionalUser.isPresent();
    }

}
