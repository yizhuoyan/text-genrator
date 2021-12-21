package com.yizhuoyan.txtgen.module.vm.service;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;

import java.util.Map;

public interface VMFileService {

    Map execute(Long vmFileId,Map input)throws Exception;
    Map execute(ViewFileEntity vmFile, Map input)throws Exception;
}
