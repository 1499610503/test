package com.example.ruijisboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruijisboot.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
