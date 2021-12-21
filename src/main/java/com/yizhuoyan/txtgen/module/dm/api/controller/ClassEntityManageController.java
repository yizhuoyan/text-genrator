package com.yizhuoyan.txtgen.module.dm.api.controller;

import com.yizhuoyan.txtgen.module.dm.ao.ClassAddAo;
import com.yizhuoyan.txtgen.module.dm.ao.ClassModAo;
import com.yizhuoyan.txtgen.module.dm.ao.ClassQueryAo;
import com.yizhuoyan.txtgen.module.dm.api.req.ClassAddReq;
import com.yizhuoyan.txtgen.module.dm.api.req.ClassModReq;
import com.yizhuoyan.txtgen.module.dm.api.req.ClassQueryReq;
import com.yizhuoyan.txtgen.module.dm.api.resp.ClassEntityDetailVo;
import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import com.yizhuoyan.txtgen.module.dm.entity.ClassTypeEnum;
import com.yizhuoyan.txtgen.module.dm.service.ClassEntityManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/class")
public class ClassEntityManageController {

    private final ClassEntityManageService manageService;

    @GetMapping("/")
    public ResponseEntity<Object> list(ClassQueryReq req)throws Exception{
        ClassQueryAo ao=new ClassQueryAo();
        ao.setNameLike(req.getNameLike());
        ao.setNamespaceLike(req.getNamespaceLike());
        ao.setNameLike(req.getNameLike());
        List<ClassEntity> query = manageService.query(ao);
        return ResponseEntity.ok(query.stream().map(ClassEntityDetailVo::of).toArray());
    }

}