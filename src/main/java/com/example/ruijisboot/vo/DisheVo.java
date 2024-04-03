package com.example.ruijisboot.vo;

import com.example.ruijisboot.entity.Dishe;
import lombok.Data;

import java.util.List;

@Data
public class DisheVo extends Dishe {
    public List options;
}
