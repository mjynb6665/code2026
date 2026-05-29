package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController//返回json数据的接口
@RequestMapping("/user")
public class UserController {
    @Resource //引入service
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    //    @param  pageNum  当前页码
//    @param  pageSize  每页显示的记录数
//    @return 分页数据
 //接口请求方式:http://localhost:9090/user/selectPage?pageNum=1&pageSize=10
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name){
        PageInfo<User> pageInfo = userService.selectPage(pageNum, pageSize, name);
        return Result.success(pageInfo);

    }

    /**
     * 删除数据
     * 接口路径：/user/delete/1
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.success();
    }


    /*新增对象
    * @param user 参数的对象*/

    @PostMapping("/add")
    public Result add(@RequestBody User user){
        userService.add(user);
        return Result.success();
    }


    @PutMapping("/update")
    public Result update(@RequestBody User user){
        //必须要t填id,不然无法更新
        userService.update(user);
        return Result.success();
    }
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        User user = userService.selectById(id);
        return Result.success(user);
    }
}
