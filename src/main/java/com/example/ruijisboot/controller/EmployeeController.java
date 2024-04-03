package com.example.ruijisboot.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.ruijisboot.common.R;
import com.example.ruijisboot.entity.Employee;
import com.example.ruijisboot.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Api(tags = "用户端控制器")
@RestController
@RequestMapping("/user")
@Slf4j
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public R login(HttpServletRequest request, @RequestBody Employee employee){
        System.out.println(employee);
        System.out.println(request);
        try {
            Employee data = employeeService.login(request,employee);
            StpUtil.login(data.getId());
            return R.success(data,"登录成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }

    }

    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public R logout(HttpSession session){
        System.out.println(session);
        try {
            Boolean data = employeeService.logout(session);
            StpUtil.logout();
            return R.success(data);
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }

    }

    @SaCheckPermission("user.add")
    @ApiOperation(value = "添加用户")
    @PostMapping("/add")
    public R add(@RequestBody Employee employee){
        try {
            Boolean data = employeeService.add(employee);
            return R.success(data);
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }

    }

    @SaCheckPermission("user.get")
    @ApiOperation(value = "用户列表")
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Integer> map){
        System.out.println(map);
        try {
            IPage<Employee> data = employeeService.page(map);
            return R.success(data);
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("/detailBuId")
    public R detailBuId(long id){
        try {
//            long longId = Long.parseLong(id);
            Employee data = employeeService.detailBuId(id);
            return R.success(data);
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
}
