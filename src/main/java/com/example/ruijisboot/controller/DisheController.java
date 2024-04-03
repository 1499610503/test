package com.example.ruijisboot.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruijisboot.common.R;
import com.example.ruijisboot.entity.Dishe;
import com.example.ruijisboot.entity.DisheOption;
import com.example.ruijisboot.entity.Employee;
import com.example.ruijisboot.service.DisheOptionService;
import com.example.ruijisboot.service.DisheService;
import com.example.ruijisboot.service.EmployeeService;
import com.example.ruijisboot.vo.DisheVo;
import com.mysql.cj.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "菜品控制器")
@RestController
@RequestMapping("/dishe")
@Slf4j
public class DisheController {
    @Autowired
    public DisheService disheService;

    @Autowired
    public DisheOptionService disheOptionService;

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public R add(@RequestBody DisheVo disheVo){
        try {
            Boolean data = disheService.save(disheVo);
            System.out.println(disheVo.getOptions());
            for (int i = 0; i < disheVo.getOptions().size(); i++) {
                Map<String,String> map = (Map) disheVo.getOptions().get(i);
                DisheOption disheOption = new DisheOption();
                disheOption.setTitle(map.get("title"));
                disheOption.setValueString(map.get("valueString"));
                disheOption.setDisheId(disheVo.getId());
                System.out.println(disheOption);
//                map2.put("title",map.get("title"));
//                map2.put("values",map.get("valueString"));
                disheOptionService.save(disheOption);
            }
            return R.success(data,"成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @ApiOperation(value = "列表")
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Integer> map){
        System.out.println(map);
        try {
            int current = map.get("page");
            int size = map.get("size");
            int total = disheService.count();
            Page<Dishe> page = new Page(current,size,total);
            IPage<Dishe> data = disheService.page(page);
            return R.success(data);
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
}
