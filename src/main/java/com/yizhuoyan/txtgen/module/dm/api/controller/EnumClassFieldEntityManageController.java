package com.yizhuoyan.txtgen.module.dm.api.controller;

import com.yizhuoyan.txtgen.module.dm.ao.EnumClassFieldAddOrModAo;
import com.yizhuoyan.txtgen.module.dm.api.req.EnumClassFieldAddOrModReq;
import com.yizhuoyan.txtgen.module.dm.api.resp.EnumClassFieldDetailVo;
import com.yizhuoyan.txtgen.module.dm.entity.EnumClassFieldEntity;
import com.yizhuoyan.txtgen.module.dm.service.EnumClassFieldEntityManageService;
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
@RequestMapping("/api/enum-class/{classId}/field")
public class EnumClassFieldEntityManageController {

    private final EnumClassFieldEntityManageService manageService;

    @GetMapping("/")
    public ResponseEntity<Object> list(@PathVariable Long classId) throws Exception {
        List<EnumClassFieldEntity> list = manageService.list(classId);
        return ResponseEntity.ok(list.stream().map(EnumClassFieldDetailVo::of).toArray());
    }

    @PostMapping("/")
    public ResponseEntity<Void> addOrMod(@PathVariable Long classId, @RequestBody List<EnumClassFieldAddOrModReq> reqList) throws Exception {
        List<EnumClassFieldAddOrModAo> aoList=new ArrayList<>(reqList.size());
        for (EnumClassFieldAddOrModReq req : reqList) {
            EnumClassFieldAddOrModAo ao=new EnumClassFieldAddOrModAo();
            ao.setClassId(classId);
            ao.setDisplayName(req.getDisplayName());
            ao.setName(req.getName());
            ao.setId(req.getId());
            ao.setOrdinal(req.getOrdinal());
            ao.setRemark(req.getRemark());
            ao.setValue(req.getValue());
            aoList.add(ao);
        }
        manageService.addOrMod(aoList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<Void> remove(@PathVariable String ids, @PathVariable String classId) throws Exception {
        manageService.remove(Arrays.stream(ids.split(",")).map(Long::parseLong).toArray(Long[]::new));
        return ResponseEntity.ok().build();
    }
}