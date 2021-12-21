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
@RequestMapping("/api/data-class")
public class DataClassEntityManageController {

    private final ClassEntityManageService manageService;

    @GetMapping("/")
    public ResponseEntity<Object> load(ClassQueryReq req)throws Exception{
        ClassQueryAo ao=new ClassQueryAo();
        ao.setNameLike(req.getNameLike());
        ao.setNamespaceLike(req.getNamespaceLike());
        ao.setNameLike(req.getNameLike());
        ao.setClassType(ClassTypeEnum.CLASS_TYPE_DATA);
        List<ClassEntity> query = manageService.query(ao);
        return ResponseEntity.ok(query.stream().map(ClassEntityDetailVo::of).toArray());
    }



    @PostMapping("/")
    public ResponseEntity<Void> add(@RequestBody ClassAddReq req)throws Exception{
        ClassAddAo ao=new ClassAddAo();
        ao.setExt(req.getExt());
        ao.setDisplayName(req.getDisplayName());
        ao.setName(req.getName());
        ao.setNamespace(req.getNamespace());
        ao.setRemark(req.getRemark());
        ao.setClassType(ClassTypeEnum.CLASS_TYPE_DATA);
        manageService.add(ao);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> load(@PathVariable  Long id)throws Exception{
        ClassEntity e = manageService.load(id);
        return ResponseEntity.ok(ClassEntityDetailVo.of(e));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modify(@PathVariable  Long id,@RequestBody ClassModReq req)throws Exception{
        ClassModAo ao=new ClassModAo();
        ao.setId(id);
        ao.setExt(req.getExt());
        ao.setDisplayName(req.getDisplayName());
        ao.setName(req.getName());
        ao.setNamespace(req.getNamespace());
        ao.setRemark(req.getRemark());
        manageService.modify(ao);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<Void> remove(@PathVariable  String ids)throws Exception{
        manageService.remove(Arrays.stream(ids.split(",")).map(Long::parseLong).toArray(Long[]::new));
        return ResponseEntity.ok().build();
    }
}