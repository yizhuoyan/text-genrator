package com.yizhuoyan.txtgen.module.task.service;

import com.yizhuoyan.txtgen.module.task.ao.GenerateOneFileAo;
import com.yizhuoyan.txtgen.module.task.dto.GenerateOneFileResult;

public interface GenerateService {

    GenerateOneFileResult generate(GenerateOneFileAo ao) throws Exception;
}
