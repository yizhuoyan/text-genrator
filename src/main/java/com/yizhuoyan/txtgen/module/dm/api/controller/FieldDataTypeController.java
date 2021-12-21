package com.yizhuoyan.txtgen.module.dm.api.controller;

import com.yizhuoyan.txtgen.module.dm.ao.ClassQueryAo;
import com.yizhuoyan.txtgen.module.dm.api.resp.FieldDataTypeVo;
import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import com.yizhuoyan.txtgen.module.dm.entity.ClassTypeEnum;
import com.yizhuoyan.txtgen.module.dm.entity.FieldBaseDataTypeEnum;
import com.yizhuoyan.txtgen.module.dm.service.ClassEntityManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/data-type")
public class FieldDataTypeController {
    private final ClassEntityManageService classEntityManageService;
    @GetMapping("/")
    public ResponseEntity<Object> load()throws Exception{

        List<FieldDataTypeVo> result=new LinkedList<>();
        int ordinal=1;
        //原生枚举
        FieldBaseDataTypeEnum[] values = FieldBaseDataTypeEnum.values();
        for (FieldBaseDataTypeEnum value : values) {
            FieldDataTypeVo vo = FieldDataTypeVo.of(value);
            vo.setOrdinal(ordinal);
            result.add(vo);
        }
        ClassQueryAo ao=new ClassQueryAo();
        ao.setClassType(ClassTypeEnum.CLASS_TYPE_ENUM);
        List<ClassEntity> enums = classEntityManageService.query(ao);
        for (ClassEntity anEnum : enums) {
            FieldDataTypeVo vo = FieldDataTypeVo.of(anEnum);
            vo.setOrdinal(ordinal++);
            result.add(vo);
        }
        return ResponseEntity.ok(result);
    }
}
