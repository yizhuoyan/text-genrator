package com.yizhuoyan.txtgen.module.dm.api.controller;

import com.yizhuoyan.txtgen.module.dm.ao.DataClassFieldAddOrModAo;
import com.yizhuoyan.txtgen.module.dm.api.req.DataClassFieldAddOrModReq;
import com.yizhuoyan.txtgen.module.dm.api.resp.ClassEntityDetailVo;
import com.yizhuoyan.txtgen.module.dm.api.resp.DataClassFieldDetailVo;
import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import com.yizhuoyan.txtgen.module.dm.entity.DataClassFieldEntity;
import com.yizhuoyan.txtgen.module.dm.service.DataClassFieldEntityManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/data-class/{classId}/field")
public class DataClassFieldEntityManageController {

    private final DataClassFieldEntityManageService manageService;

    @GetMapping("/")
    public ResponseEntity<Object> list(@PathVariable Long classId)throws Exception{
        List<DataClassFieldEntity> list = manageService.list(classId);
        return ResponseEntity.ok(list.stream().map(DataClassFieldDetailVo::of).toArray());
    }
    @PostMapping("/")
    @PutMapping("/")
    public ResponseEntity<Void> addOrMod(@PathVariable Long classId,@RequestBody List<DataClassFieldAddOrModReq> reqList)throws Exception{
        List<DataClassFieldAddOrModAo> aoList=new ArrayList<>(reqList.size());
        for (DataClassFieldAddOrModReq req : reqList) {
            DataClassFieldAddOrModAo ao=new DataClassFieldAddOrModAo();
            ao.setClassId(classId);
            ao.setDataType(req.getDataType());
            ao.setExt(req.getExt());
            ao.setSubDataType(req.getSubDataType());
            ao.setDefaultValue(req.getDefaultValue());
            ao.setDisplayName(req.getDisplayName());
            ao.setId(req.getId());
            ao.setMax(req.getMax());
            ao.setMin(req.getMin());
            ao.setName(req.getName());
            ao.setPrimary(req.getPrimary());
            ao.setOrdinal(req.getOrdinal());
            ao.setUnique(req.getUnique());
            ao.setRemark(req.getRemark());
            ao.setRequired(req.getRequired());
            ao.setPattern(req.getPattern());
            aoList.add(ao);
        }
        manageService.addOrMod(aoList);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/batch/")
    public ResponseEntity<Void> fieldBatch(@PathVariable Long classId,@RequestBody List<DataClassFieldAddOrModReq> reqList)throws Exception{
        List<DataClassFieldAddOrModAo> aoList=new ArrayList<>(reqList.size());
        for (DataClassFieldAddOrModReq req : reqList) {
            DataClassFieldAddOrModAo ao=new DataClassFieldAddOrModAo();
            ao.setClassId(classId);
            ao.setDataType(req.getDataType());
            ao.setExt(req.getExt());
            ao.setSubDataType(req.getSubDataType());
            ao.setDefaultValue(req.getDefaultValue());
            ao.setDisplayName(req.getDisplayName());
            ao.setId(req.getId());
            ao.setMax(req.getMax());
            ao.setMin(req.getMin());
            ao.setName(req.getName());
            ao.setPrimary(req.getPrimary());
            ao.setOrdinal(req.getOrdinal());
            ao.setUnique(req.getUnique());
            ao.setRemark(req.getRemark());
            ao.setRequired(req.getRequired());
            ao.setPattern(req.getPattern());
            aoList.add(ao);
        }
        manageService.replaceFields(classId,aoList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<Void> remove(@PathVariable Long classId,@PathVariable  String ids)throws Exception{
        manageService.remove(Arrays.stream(ids.split(",")).map(Long::parseLong).toArray(Long[]::new));
        return ResponseEntity.ok().build();
    }
}