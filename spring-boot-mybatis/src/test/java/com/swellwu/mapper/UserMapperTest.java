package com.swellwu.mapper;

import com.swellwu.enums.UserSexEnum;
import com.swellwu.po.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author swellwu
 * @create 2017-08-14-21:34
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
public class UserMapperTest {

    @Autowired
    private UserMapper UserMapper;

    @Test
    public void testInsert() throws Exception {
        UserMapper.insert(new User("aa", "a123456", UserSexEnum.MAN));
        UserMapper.insert(new User("bb", "b123456", UserSexEnum.WOMAN));
        UserMapper.insert(new User("cc", "b123456", UserSexEnum.WOMAN));

        Assert.assertEquals(3, UserMapper.getAll().size());
    }

    @Test
    public void testQuery() throws Exception {
        List<User> users = UserMapper.getAll();
        if(users==null || users.size()==0){
            System.out.println("is null");
        }else{
            System.out.println(users.toString());
        }
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User("aa", "a123456", UserSexEnum.MAN);
        UserMapper.insert(user);
        user = UserMapper.getOne(user.getId());
        System.out.println(user.toString());
        user.setNickName("neo");
        UserMapper.update(user);
        Assert.assertTrue(("neo".equals(UserMapper.getOne(user.getId()).getNickName())));
    }

}