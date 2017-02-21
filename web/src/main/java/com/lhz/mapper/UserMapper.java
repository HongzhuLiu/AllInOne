package com.lhz.mapper;

import com.lhz.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by LHZ on 2017/1/18.
 */
@Mapper
public interface UserMapper extends CrudMapper<User> {
    User selectByUsername(String username);

    int updatePassword(User user);

    int updatePhone(User user);
}
