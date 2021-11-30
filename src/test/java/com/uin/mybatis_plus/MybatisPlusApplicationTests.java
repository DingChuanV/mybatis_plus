package com.uin.mybatis_plus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uin.mapper.UserMapper;
import com.uin.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void select() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void insert() {
        User user = new User();
        user.setAge(18);
        user.setEmail("2686221966@qq.com");
        user.setName("uin");
        userMapper.insert(user);
        System.out.println(user);
    }

    @Test
    void update() {
        User user = new User();
        user.setId(5L);
        user.setName("wanglufei");
        user.setAge(18);
        userMapper.updateById(user);
    }

    //测试乐观锁
    @Test
    void Lock() {
        //查询用户 带version
        User user = userMapper.selectById(1l);
        user.setName("uin");
        userMapper.updateById(user);
    }

    //测试
    @Test
    void Lock2() {
        //查询用户 带version
        User user = userMapper.selectById(1l);
        user.setName("uin");

        User user2 = userMapper.selectById(1l);
        user2.setName("uin222");
        userMapper.updateById(user2);
        //发现只有user2 成功
        //可以使用自旋锁操作 轮训来尝试
        userMapper.updateById(user);

    }

    //select 1个用户
    @Test
    void select1() {
        User user = userMapper.selectById(1l);
        System.out.println(user);
    }

    //select 多个用户
    @Test
    void selectbatch() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3, 4));
        users.forEach(System.out::println);
    }

    //条件查询
    @Test
    void where() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "uin");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试分页查询
    @Test
    void pagehelper() {
        Page<User> page = new Page<>(2, 5);
        Page<User> userPage = userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
    }

    //删除操作
    @Test
    void delete() {
//        int i = userMapper.deleteById(1465571908895424518l);
//        userMapper.deleteBatchIds(Arrays.asList(1465571908895424518l,1465571908895424518l));
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 1465571908895424518l);
        userMapper.deleteByMap(map);
    }

    //逻辑删除
    /**
     * 物理删除：从数据库中直接删除
     * 逻辑删除：再数据库中没有被移除，而是通过一个变量来让他失效！
     *
     * 管理员可以查看被删除的记录！防止数据的丢失！
     */
}
