package com.lhz.service;

import com.lhz.entity.User;

/**
 * Created by LHZ on 2016/12/26.
 */
public interface UserService{
    User selectUserByUsername(String username);
    boolean updateUserPassword(User user);
}
