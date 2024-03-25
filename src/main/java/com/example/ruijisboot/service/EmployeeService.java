package com.example.ruijisboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruijisboot.common.R;
import com.example.ruijisboot.entity.Employee;
import com.google.protobuf.Internal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public interface EmployeeService extends IService<Employee> {
    public Employee login(HttpServletRequest request, Employee employee) throws Exception;

    public Boolean add(Employee employee) throws Exception;

    public Boolean logout(HttpSession session);

    public IPage page(Map<String, Integer> map);

    public Employee detailBuId(long id);
}
