package com.swellwu.mapper;

import com.swellwu.po.User;

import java.util.List;

/**
 * @author swellwu
 * @create 2017-08-14-21:18
 */
public interface UserMapper {

    List<User> getAll();

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);

}
