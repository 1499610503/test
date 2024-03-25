package com.example.ruijisboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruijisboot.common.R;
import com.example.ruijisboot.entity.Employee;
import com.example.ruijisboot.service.EmployeeService;
import com.google.protobuf.Internal;
import com.mysql.cj.Query;
import jdk.nashorn.internal.ir.RuntimeNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Slf4j
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    @PostMapping("/login")
    public R login(HttpServletRequest request, @RequestBody Employee employee){
        System.out.println(employee);
        System.out.println(request);
        try {
            Employee data = employeeService.login(request,employee);
            return R.success(data,"登录成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }

    }

    @PostMapping("/logout")
    public R logout(HttpSession session){
        System.out.println(session);
        try {
            Boolean data = employeeService.logout(session);
            return R.success(data);
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }

    }

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
