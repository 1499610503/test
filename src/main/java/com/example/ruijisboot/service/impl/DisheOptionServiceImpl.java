package com.example.ruijisboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruijisboot.entity.Dishe;
import com.example.ruijisboot.entity.DisheOption;
import com.example.ruijisboot.mapper.DisheMapper;
import com.example.ruijisboot.mapper.DisheOptionMapper;
import com.example.ruijisboot.service.DisheOptionService;
import com.example.ruijisboot.service.DisheService;
import org.springframework.stereotype.Service;

@Service
public class DisheOptionServiceImpl extends ServiceImpl<DisheOptionMapper, DisheOption> implements DisheOptionService {

}