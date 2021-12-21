package com.yizhuoyan.txtgen.module.task.api.controller;

import com.yizhuoyan.txtgen.module.task.ao.TaskAddAo;
import com.yizhuoyan.txtgen.module.task.ao.TaskListAo;
import com.yizhuoyan.txtgen.module.task.ao.TaskModAo;
import com.yizhuoyan.txtgen.module.task.api.req.TaskAddReq;
import com.yizhuoyan.txtgen.module.task.api.req.TaskListReq;
import com.yizhuoyan.txtgen.module.task.api.req.TaskModReq;
import com.yizhuoyan.txtgen.module.task.api.resp.TaskEntityDetailVo;
import com.yizhuoyan.txtgen.module.task.api.resp.TaskEntitySimpleVo;
import com.yizhuoyan.txtgen.module.task.dto.GenerateOneFileResult;
import com.yizhuoyan.txtgen.module.task.entity.TaskEntity;
import com.yizhuoyan.txtgen.module.task.service.TaskManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final TaskManageService manageService;

    @GetMapping("/")
    public ResponseEntity<Object> load(TaskListReq req)throws Exception{
        TaskListAo ao=new TaskListAo();
        ao.setName(req.getName());
        ao.setNamespace(req.getNamespace());
        List<TaskEntity> query = manageService.query(ao);
        return ResponseEntity.ok(query.stream().map(TaskEntitySimpleVo::of).toArray());
    }

    @PostMapping("/")
    public ResponseEntity<Void> add(@RequestBody TaskAddReq req)throws Exception{
        TaskAddAo ao=new TaskAddAo();
        ao.setName(req.getName());
        ao.setNamespace(req.getNamespace());
        manageService.add(ao);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}/execute/")
    public void execute(@PathVariable  Long id, HttpServletResponse resp)throws Exception{
        List<GenerateOneFileResult> executeResultList = manageService.execute(id);
        resp.setContentType("application/oct-stream");
        resp.setHeader("Content-Disposition", "attachment; filename="+ LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS'.zip'")));
        try(ZipOutputStream zipOutputStream=new ZipOutputStream(resp.getOutputStream(), StandardCharsets.UTF_8);){
            for (GenerateOneFileResult generateOneFileResult : executeResultList) {
                zipOutputStream.putNextEntry(new ZipEntry(generateOneFileResult.getPath()));
                try(InputStream inputStream = generateOneFileResult.getInputStream()){
                    byte[] buff=new byte[1024*10];
                    int readed=0;
                    while((readed=inputStream.read(buff))!=-1){
                        zipOutputStream.write(buff,0,readed);
                    }
                    zipOutputStream.closeEntry();
                }
            }
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> load(@PathVariable  Long id)throws Exception{
        TaskEntity e = manageService.load(id);
        return ResponseEntity.ok(TaskEntityDetailVo.of(e));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> modify(@PathVariable  Long id,@RequestBody TaskModReq req)throws Exception{
        TaskModAo ao=new TaskModAo();
        ao.setId(id);
        ao.setDataModels(req.getDataModels());
        ao.setTemplateFiles(req.getTemplateFiles());
        ao.setName(req.getName());
        ao.setNamespace(req.getNamespace());
        ao.setRemark(req.getRemark());
        ao.setTaskConfig(req.getTaskConfig());
        manageService.modify(ao);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<Void> remove(@PathVariable  String ids)throws Exception{
        manageService.remove(Arrays.stream(ids.split(",")).map(Long::parseLong).toArray(Long[]::new));
        return ResponseEntity.ok().build();
    }
}