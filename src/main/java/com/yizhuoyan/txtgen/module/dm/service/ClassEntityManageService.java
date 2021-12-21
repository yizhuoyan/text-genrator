package com.yizhuoyan.txtgen.module.dm.service;

import com.yizhuoyan.txtgen.module.dm.ao.ClassAddAo;
import com.yizhuoyan.txtgen.module.dm.ao.ClassModAo;
import com.yizhuoyan.txtgen.module.dm.ao.ClassQueryAo;
import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Validated
public interface ClassEntityManageService {

    void add(@Valid ClassAddAo ao)throws Exception;
    void modify(@Valid ClassModAo ao)throws Exception;
    ClassEntity load(Long  id)throws Exception;
    List<ClassEntity> query(ClassQueryAo ao)throws Exception;
    void remove(Long... ids)throws Exception;

    Map<String,Object> loadModel(Long dmId);

}
