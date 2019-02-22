package com.bluesgao.swaggerdemo.controller;

import com.bluesgao.swaggerdemo.common.Result;
import com.bluesgao.swaggerdemo.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@RestController
public class UserController {
    // 创建线程安全的Map
    static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<Integer, User>());

    /**
     * 根据ID查询用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Result> getUserById(@PathVariable(value = "id") Integer id) {
        Result r = null;
        try {
            User user = users.get(id);
            r = Result.builder().code(0).info("ok").value(user).build();
        } catch (Exception e) {
            r = Result.builder().code(9999).info("error").build();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 查询用户列表
     *
     * @return
     */
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity<Result> getUserList() {
        Result r = null;
        try {
            List<User> userList = new ArrayList<User>(users.values());
            r = Result.builder().code(0).info("ok").value(userList).build();
        } catch (Exception e) {
            r = Result.builder().code(9999).info("error").build();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ResponseEntity<Result> add(@RequestBody User user) {
        Result r = null;
        try {
            users.put(user.getId(), user);
            r = Result.builder().code(0).info("ok").value(user).build();

        } catch (Exception e) {
            r = Result.builder().code(9999).info("error").build();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Result> delete(@PathVariable(value = "id") Integer id) {
        Result r = null;
        try {
            users.remove(id);
            r = Result.builder().code(0).info("ok").value(id).build();
        } catch (Exception e) {
            r = Result.builder().code(9999).info("error").build();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 根据id修改用户信息
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "更新信息", notes = "根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Result> update(@PathVariable("id") Integer id, @RequestBody User user) {
        Result r = null;
        try {
            User u = users.get(id);
            u.setUsername(user.getUsername());
            u.setAge(user.getAge());
            users.put(id, u);
            r = Result.builder().code(0).info("ok").value(u).build();
        } catch (Exception e) {
            r = Result.builder().code(9999).info("error").build();
        }
        return ResponseEntity.ok(r);
    }

    @ApiIgnore//使用该注解忽略这个API
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String jsonTest() {
        return " hi you!";
    }
}
