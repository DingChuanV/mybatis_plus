package com.uin.mybatis_plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uin.mapper.UserMapper;
import com.uin.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.plaf.synth.SynthEditorPaneUI;

/**
 * @author wanglufei
 * @description: TODO
 * @date 2021/11/30/7:25 下午
 */
@SpringBootTest
public class WrapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void select() {
        //查询name不为空的用户，并且邮箱不为空的，年龄大于等于19
        userMapper.selectList(new QueryWrapper<User>().isNotNull("name")
                        .isNotNull("email").ge("age", 19))
                .forEach(System.out::println);
    }

    @Test
    void select2() {
        //name=uin的
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("name", "uin"));
        System.out.println(user);
    }

    @Test
    void select3() {
        //查询年龄在19-20之间的
        Long age = userMapper.selectCount(new QueryWrapper<User>()
                .between("age", 19, 20));
        System.out.println(age);
    }

    //模糊查询
    @Test
    void select4() {

        userMapper.selectMaps(new QueryWrapper<User>()
                .notLike("name", "u")
                //右 t%
                //左 %t
                .likeRight("email", "t")).forEach(System.out::println);
    }

    @Test
    void select5() {
        // in 查询
        // ID 在子查询中查出来
        userMapper.selectObjs(new QueryWrapper<User>()
                .inSql("id", "select id from user where id<3")).forEach(System.out::println);
    }

    //排序
    @Test
    void orderBy() {
        //通过id进行排序
        userMapper.selectList(new QueryWrapper<User>()
                        .orderByDesc("id"))
                .forEach(System.out::println);
    }
}
