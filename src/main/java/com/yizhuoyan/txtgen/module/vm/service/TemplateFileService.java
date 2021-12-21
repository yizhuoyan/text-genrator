package com.yizhuoyan.txtgen.module.vm.service;

import com.yizhuoyan.txtgen.module.vm.ao.TemplatePreviewAo;
import com.yizhuoyan.txtgen.module.vm.dto.TemplateViewPreviewDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface TemplateFileService {

    TemplateViewPreviewDto preview(@Valid TemplatePreviewAo ao)throws Exception;
}
