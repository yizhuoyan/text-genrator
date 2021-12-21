package com.yizhuoyan.txtgen.module.vm.api.resp;

import com.yizhuoyan.txtgen.module.vm.dto.TemplateViewPreviewDto;
import lombok.Data;

@Data
public class TemplateViewPreviewVo {
    String resultPath;
    String resultContent;

    public static TemplateViewPreviewVo of(TemplateViewPreviewDto dto) {
        TemplateViewPreviewVo vo=new TemplateViewPreviewVo();
        vo.setResultContent(dto.getResultContent());
        vo.setResultPath(dto.getResultPath());
        return vo;
    }
}
