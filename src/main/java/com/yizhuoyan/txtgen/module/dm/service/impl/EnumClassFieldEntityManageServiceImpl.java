package com.yizhuoyan.txtgen.module.dm.service.impl;

import cn.hutool.core.bean.BeanDesc;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.PropDesc;
import com.yizhuoyan.common.exception.DataNotFoundException;
import com.yizhuoyan.txtgen.module.dm.ao.EnumClassFieldAddOrModAo;
import com.yizhuoyan.txtgen.module.dm.dao.ClassEntityDao;
import com.yizhuoyan.txtgen.module.dm.dao.EnumClassFieldEntityDao;
import com.yizhuoyan.txtgen.module.dm.entity.DataClassFieldEntity;
import com.yizhuoyan.txtgen.module.dm.entity.EnumClassFieldEntity;
import com.yizhuoyan.txtgen.module.dm.service.EnumClassFieldEntityManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnumClassFieldEntityManageServiceImpl implements EnumClassFieldEntityManageService {

    private final EnumClassFieldEntityDao enumClassFieldEntityDao;
    private final ClassEntityDao classEntityDao;
    @Override
    public List<EnumClassFieldEntity> list(Long classId) throws Exception {
        return enumClassFieldEntityDao.findByBelongClassOrderByOrdinal(classEntityDao.getById(classId));
    }

    @Override
    @Transactional
    public void addOrMod(List<EnumClassFieldAddOrModAo> aos) throws Exception {
        BeanDesc aoBeanDesc = BeanUtil.getBeanDesc(EnumClassFieldAddOrModAo.class);
        BeanDesc oldBeanDesc = BeanUtil.getBeanDesc(EnumClassFieldEntity.class);
        Collection<PropDesc> aoProps = aoBeanDesc.getProps();
        for (EnumClassFieldAddOrModAo ao : aos) {
            if(ao.getId()==null){
                //新增
                EnumClassFieldEntity e=new EnumClassFieldEntity();
                BeanUtil.copyProperties(ao, e);
                e.setBelongClass(classEntityDao.getById(ao.getClassId()));
                enumClassFieldEntityDao.save(e);
            }else{
                //修改
                EnumClassFieldEntity old = enumClassFieldEntityDao.findById(ao.getId()).orElseThrow(()-> new DataNotFoundException("数据无法找到"));
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
        enumClassFieldEntityDao.deleteAllByIdInBatch(Arrays.asList(ids));
    }
}
