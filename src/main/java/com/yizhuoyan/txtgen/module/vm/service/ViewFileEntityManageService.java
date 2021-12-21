package com.yizhuoyan.txtgen.module.vm.service;

import com.yizhuoyan.txtgen.module.vm.ao.TemplateViewFileQueryAo;
import com.yizhuoyan.txtgen.module.vm.ao.ViewFileModAo;
import com.yizhuoyan.txtgen.module.vm.ao.ViewFileAddAo;
import com.yizhuoyan.txtgen.module.vm.ao.ViewFileQueryAo;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
@Validated
public interface ViewFileEntityManageService {

    void add(@Valid ViewFileAddAo ao)throws Exception;
    void modify(@Valid ViewFileModAo ao)throws Exception;
    ViewFileEntity load(Long  id)throws Exception;
    List<ViewFileEntity> query(ViewFileQueryAo ao)throws Exception;
    List<ViewFileEntity> queryTemplateView(TemplateViewFileQueryAo ao)throws Exception;
    void remove(Long... ids)throws Exception;

    ViewFileEntity getByExample(ViewFileEntity exp)throws Exception;


}
