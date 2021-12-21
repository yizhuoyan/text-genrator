package com.yizhuoyan.txtgen.module.vm.api.controller;

import com.yizhuoyan.txtgen.module.vm.ao.*;
import com.yizhuoyan.txtgen.module.vm.api.req.*;
import com.yizhuoyan.txtgen.module.vm.api.resp.TemplateViewPreviewVo;
import com.yizhuoyan.txtgen.module.vm.api.resp.ViewEntityDetailVo;
import com.yizhuoyan.txtgen.module.vm.api.resp.ViewEntitySimpleVo;
import com.yizhuoyan.txtgen.module.vm.dto.TemplateViewPreviewDto;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;
import com.yizhuoyan.txtgen.module.vm.service.TemplateFileService;
import com.yizhuoyan.txtgen.module.vm.service.ViewFileEntityManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/view")
public class ViewEntityManageController {

    final ViewFileEntityManageService manageService;
    final TemplateFileService templateFileService;


    @GetMapping("/")
    public ResponseEntity<Object> list(ViewFileQueryReq req)throws Exception{
        ViewFileQueryAo ao=new ViewFileQueryAo();
        ao.setFileType(req.getFileType());
        ao.setNameLike(req.getNameLike());
        ao.setNamespaceLike(req.getNamespaceLike());
        List<ViewFileEntity> query = manageService.query(ao);
        return ResponseEntity.ok(query.stream().map(ViewEntitySimpleVo::of).toArray());
    }

    @GetMapping("/template/")
    public ResponseEntity<Object> listTemplateView(TemplateViewFileListReq req)throws Exception{
        TemplateViewFileQueryAo ao=new TemplateViewFileQueryAo();
        ao.setNameLike(req.getNameLike());
        ao.setNamespaceLike(req.getNamespaceLike());
        List<ViewFileEntity> query = manageService.queryTemplateView(ao);
        return ResponseEntity.ok(query.stream().map(ViewEntitySimpleVo::of).toArray());
    }

    @PostMapping("/preview/")
    public ResponseEntity<Object> preview(@RequestBody TemplatePreviewReq req)throws Exception{
        TemplatePreviewAo ao=new TemplatePreviewAo();
        ao.setDmId(req.getDmId());
        ao.setVmId(req.getVmId());
        TemplateViewPreviewDto dto = templateFileService.preview(ao);
        return ResponseEntity.ok(TemplateViewPreviewVo.of(dto));
    }

    @PostMapping("/")
    public ResponseEntity<Void> add(@RequestBody ViewFileAddReq req)throws Exception{
        ViewFileAddAo ao=new ViewFileAddAo();
        ao.setFileType(req.getFileType());
        ao.setName(req.getName());
        ao.setNamespace(req.getNamespace());
        ao.setFileContent(req.getFileContent());
        ao.setGeneratePath(req.getGeneratePath());
        manageService.add(ao);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> load(@PathVariable  Long id)throws Exception{
        ViewFileEntity e = manageService.load(id);
        return ResponseEntity.ok(ViewEntityDetailVo.of(e));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> modify(@PathVariable  Long id,@RequestBody ViewFileModReq req)throws Exception{
        ViewFileModAo ao=new ViewFileModAo();
        ao.setName(req.getName());
        ao.setNamespace(req.getNamespace());
        ao.setFileContent(req.getFileContent());
        ao.setGeneratePath(req.getGeneratePath());
        ao.setId(id);
        manageService.modify(ao);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<Void> remove(@PathVariable  String ids)throws Exception{
        manageService.remove(Arrays.stream(ids.split(",")).map(Long::parseLong).toArray(Long[]::new));
        return ResponseEntity.ok().build();
    }
}