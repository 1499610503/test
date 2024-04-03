package com.example.ruijisboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruijisboot.entity.Dishe;
import com.example.ruijisboot.entity.Employee;
import com.example.ruijisboot.mapper.DisheMapper;
import com.example.ruijisboot.mapper.EmployeeMapper;
import com.example.ruijisboot.service.DisheService;
import com.example.ruijisboot.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class DisheServiceImpl extends ServiceImpl<DisheMapper, Dishe> implements DisheService {

}
