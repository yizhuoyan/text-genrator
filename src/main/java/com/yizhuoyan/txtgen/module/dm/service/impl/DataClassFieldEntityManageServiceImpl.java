package com.yizhuoyan.txtgen.module.dm.service.impl;

import cn.hutool.core.bean.BeanDesc;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.PropDesc;
import com.yizhuoyan.common.exception.DataNotFoundException;
import com.yizhuoyan.txtgen.module.dm.ao.DataClassFieldAddOrModAo;
import com.yizhuoyan.txtgen.module.dm.dao.ClassEntityDao;
import com.yizhuoyan.txtgen.module.dm.dao.DataClassFieldEntityDao;
import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import com.yizhuoyan.txtgen.module.dm.entity.DataClassFieldEntity;
import com.yizhuoyan.txtgen.module.dm.service.DataClassFieldEntityManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataClassFieldEntityManageServiceImpl  implements DataClassFieldEntityManageService {

    private final DataClassFieldEntityDao dataClassFieldEntityDao;
    private final ClassEntityDao classEntityDao;
    @Override
    public List<DataClassFieldEntity> list(Long classId) throws Exception {
        return dataClassFieldEntityDao.findByBelongClassOrderByOrdinal(classEntityDao.getById(classId));
    }

    @Transactional
    @Override
    public void replaceFields(Long classId,List<DataClassFieldAddOrModAo> aoList) throws Exception{
        ClassEntity belongClass = classEntityDao.getById(classId);
        dataClassFieldEntityDao.deleteByBelongClass(belongClass);
        for (DataClassFieldAddOrModAo ao : aoList) {
            //新增
            DataClassFieldEntity e=new DataClassFieldEntity();
            BeanUtil.copyProperties(ao, e);
            e.setBelongClass(belongClass);
            dataClassFieldEntityDao.save(e);
        }
    }

    private String determineExt(String ext){

        return ext;
    }

    @Transactional
    @Override
    public void addOrMod(List<DataClassFieldAddOrModAo> aos) throws Exception {
        BeanDesc aoBeanDesc = BeanUtil.getBeanDesc(DataClassFieldAddOrModAo.class);
        BeanDesc oldBeanDesc = BeanUtil.getBeanDesc(DataClassFieldEntity.class);
        Collection<PropDesc> aoProps = aoBeanDesc.getProps();
        for (DataClassFieldAddOrModAo ao : aos) {
            if(ao.getId()==null){
                //新增
                DataClassFieldEntity e=new DataClassFieldEntity();
                BeanUtil.copyProperties(ao, e);
                e.setBelongClass(classEntityDao.getById(ao.getClassId()));
                dataClassFieldEntityDao.save(e);
            }else{
                //修改
                DataClassFieldEntity old = dataClassFieldEntityDao.findById(ao.getId()).orElseThrow(()-> new DataNotFoundException("数据无法找到"));
                for (PropDesc aoProp : aoProps) {
                    String propName=aoProp.getFieldName();
                    if("classId".equals(propName)||"id".equals(propName)){
                        continue;
                    }
                    Object newValue = aoProp.getGetter().invoke(ao);
                    if(newValue!=null&& !Objects.equals(newValue, oldBeanDesc.getGetter(propName).invoke(old))){
                        oldBeanDesc.getSetter(propName).invoke(old,newValue);
                    }

                }

            }
        }
    }
    @Override
    public void remove(Long... ids) throws Exception {
        dataClassFieldEntityDao.deleteAllByIdInBatch(Arrays.asList(ids));
    }
}
