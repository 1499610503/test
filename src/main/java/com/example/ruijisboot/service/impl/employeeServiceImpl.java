package com.example.ruijisboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruijisboot.common.R;
import com.example.ruijisboot.entity.Employee;
import com.example.ruijisboot.mapper.EmployeeMapper;
import com.example.ruijisboot.service.EmployeeService;
import com.google.protobuf.Internal;
import com.mysql.cj.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class employeeServiceImpl  extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Resource
    public EmployeeMapper employeeMapper;

    @Autowired
    public RedisTemplate redisTemplate;

    public Employee login(HttpServletRequest request, Employee employee) throws Exception {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", employee.getName());
        System.out.println(queryWrapper);
        Employee employee1 = employeeMapper.selectOne(queryWrapper);
        if (employee1 == null){
            throw  new Exception("用户不存在");
        }
        if (!employee1.getPassword().equals(DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()))){
            System.out.println(employee1.getPassword());
            System.out.println(DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()));
            throw  new Exception("账号或密码不对");
        }

        HttpSession session = request.getSession();
        session.setAttribute("userId",employee1.getId());

//        redisTemplate.opsForValue().set("userId",employee1.getId());

        return  employee1;
    }

    public Boolean add(Employee employee) throws Exception {
        String pass = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        System.out.println(pass);
        // 判断是否已存在
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Employee::getName,employee.getName());
        int count = this.count(lambdaQueryWrapper);
        if (count>0){
            throw new Exception("用户已存在");
        }
        employee.setPassword(pass);
        return this.save(employee);
    }

    @CacheEvict(value = "employeeCache", allEntries = true,beforeInvocation = true)
    public Boolean logout(HttpSession session){
        session.removeAttribute("userId");
//        redisTemplate.delete("userId");
        return  true;
    }

    public IPage page(Map<String, Integer> map){
            System.out.println(map);
            int current = map.get("page");
            int size = map.get("size");
            Page<Employee> page = new Page(current, size,4);
            IPage<Employee> data = this.page(page);
            int count = this.count();
            data.setTotal(count);
            System.out.println(data);
            return data;
    }

    @Cacheable(value = "employeeCache", key = "#id", unless = "#result==null")
//    @Cacheable("employeeCache")
    public Employee detailBuId(long id){
        Employee employee = this.getById(id);
//        redisTemplate.opsForValue().set(id+"",employee);
        return  employee;
    }
}
