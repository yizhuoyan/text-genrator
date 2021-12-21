package com.yizhuoyan.txtgen.module.vm.service.impl;

import cn.hutool.core.io.IoUtil;
import com.yizhuoyan.txtgen.module.task.ao.GenerateOneFileAo;
import com.yizhuoyan.txtgen.module.task.dto.GenerateOneFileResult;
import com.yizhuoyan.txtgen.module.task.service.GenerateService;
import com.yizhuoyan.txtgen.module.vm.ao.TemplatePreviewAo;
import com.yizhuoyan.txtgen.module.vm.dto.TemplateViewPreviewDto;
import com.yizhuoyan.txtgen.module.vm.service.TemplateFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Service
public class TemplateFileServiceImpl implements TemplateFileService {
    final GenerateService generateService;
    @Override
    public TemplateViewPreviewDto preview(@Valid TemplatePreviewAo ao) throws Exception {
        GenerateOneFileAo generateOneFileAo=new GenerateOneFileAo();
        generateOneFileAo.setDmId(ao.getDmId());
        generateOneFileAo.setTemplateId(ao.getVmId());
        generateOneFileAo.setConfigMap(null);
        GenerateOneFileResult generateResult = generateService.generate(generateOneFileAo);

        TemplateViewPreviewDto dto=new TemplateViewPreviewDto();
        dto.setResultPath(generateResult.getPath());
        dto.setResultContent(IoUtil.read(generateResult.getInputStream(),"utf-8"));
        return dto;
    }
}
