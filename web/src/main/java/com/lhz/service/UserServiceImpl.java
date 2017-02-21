package com.lhz.service;

import com.lhz.entity.User;
import com.lhz.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LHZ on 2016/12/26.
 */
@Service("userService")
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Cacheable
    @Override
    public User selectUserByUsername(String username) {
        System.out.println("没有走缓存！" + username);
        return userMapper.selectByUsername(username);
    }


    @Override
    @Transactional
    public boolean updateUserPassword(User user){
        return userMapper.update(user)>0;
    }
}
