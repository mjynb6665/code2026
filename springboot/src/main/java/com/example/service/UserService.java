package com.example.service;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
//    @param  pageNum  当前页码
//    @param  pageSize  每页显示的记录数
//    @return 分页数据

    @Resource //引入service
    private UserMapper userMapper;

    public PageInfo<User> selectPage(Integer pageNum, Integer pageSize, String name)
    {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(name);
        return PageInfo.of(list);
    }

    public void deleteById(Integer id)
    {
        userMapper.deleteById(id);
    }

    public void add(User user) {
        String username = user.getUsername();
        User dbUser = userMapper.selectByusername(username);

        if(dbUser != null){
            throw new CustomException("账号重复,用户名已存在");
        }
        //默认密码123
        if(StrUtil.isBlank(user.getPassword())){
            user.setPassword("123");
        }
        //默认昵称为用户名
        if(StrUtil.isBlank(user.getName())){
            user.setName(user.getUsername());
        }
        user.setRole("普通用户");
        user.setAccount(BigDecimal.ZERO);//账户余额
        userMapper.insert(user);
    }
    public void update(User user) {
        // user对象里面必须包含ID，否则无法更新数据
        userMapper.updateByid(user);
    }

    public Account login(Account account) {
        User user = userMapper.selectByusername(account.getUsername());
        if (ObjectUtil.isNull(user)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(user.getPassword())) {
            throw new CustomException("账号或密码错误");
        }
        return user;
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByusername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException("账号或密码错误");
        }
       dbUser.setPassword(account.getNewPassword());
        userMapper.updateByid(dbUser);

    }
}
