package com.yizhuoyan.txtgen.module.dm.service;

import com.yizhuoyan.txtgen.module.dm.ao.DataClassFieldAddOrModAo;
import com.yizhuoyan.txtgen.module.dm.entity.DataClassFieldEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
@Validated
public interface DataClassFieldEntityManageService {

    List<DataClassFieldEntity> list(Long classId)throws Exception;

    void addOrMod(@Valid List<DataClassFieldAddOrModAo> aos)throws Exception;

    void remove(Long... ids)throws Exception;

    void replaceFields(Long classId,List<DataClassFieldAddOrModAo> aoList)throws Exception;
}
