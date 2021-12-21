package com.yizhuoyan.txtgen.module.dm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yizhuoyan.common.exception.DataNotFoundException;
import com.yizhuoyan.txtgen.module.dm.ao.ClassAddAo;
import com.yizhuoyan.txtgen.module.dm.ao.ClassModAo;
import com.yizhuoyan.txtgen.module.dm.ao.ClassQueryAo;
import com.yizhuoyan.txtgen.module.dm.dao.ClassEntityDao;
import com.yizhuoyan.txtgen.module.dm.dao.DataClassFieldEntityDao;
import com.yizhuoyan.txtgen.module.dm.dao.EnumClassFieldEntityDao;
import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import com.yizhuoyan.txtgen.module.dm.entity.ClassTypeEnum;
import com.yizhuoyan.txtgen.module.dm.entity.DataClassFieldEntity;
import com.yizhuoyan.txtgen.module.dm.entity.EnumClassFieldEntity;
import com.yizhuoyan.txtgen.module.dm.service.ClassEntityManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassEntityManageServiceImpl implements ClassEntityManageService {

    private final ClassEntityDao classEntityDao;
    private final DataClassFieldEntityDao dataClassFieldEntityDao;
    private final EnumClassFieldEntityDao enumClassFieldEntityDao;



    public Map<String,Object> loadModel(Long dmId){
        Map<String,Object> result=new HashMap<>();
        ClassEntity e = classEntityDao.getById(dmId);
        result.putAll(e.toBeanMap());
        //字段
        if(e.getClassType()== ClassTypeEnum.CLASS_TYPE_DATA){
            List<DataClassFieldEntity> fields = dataClassFieldEntityDao.findByBelongClassOrderByOrdinal(e);
            result.put("fields",fields.stream().map(DataClassFieldEntity::toBeanMap).toArray());
        }else if(e.getClassType()==ClassTypeEnum.CLASS_TYPE_ENUM){
            List<EnumClassFieldEntity> fields = enumClassFieldEntityDao.findByBelongClassOrderByOrdinal(e);
            result.put("fields",fields.stream().map(EnumClassFieldEntity::toBeanMap).toArray());
        }
        return result;
    }


    @Override
    public void add(ClassAddAo ao) throws Exception {
        ClassEntity e=new ClassEntity();
        e.setDisplayName(ao.getDisplayName());
        e.setRemark(ao.getRemark());
        e.setNamespace(ao.getNamespace());
        e.setName(ao.getName());
        e.setClassType(ao.getClassType());
        e.setExt(ao.getExt());
        classEntityDao.save(e);
    }

    @Override
    public void modify(ClassModAo ao) throws Exception {
        ClassEntity old= classEntityDao.getById(ao.getId());
        if(old==null){
            throw new DataNotFoundException("数据{}不存在", ao.getId());
        }
        String newName=ao.getName();
        if(newName!=null&& !Objects.equals(newName, old.getName())){
            old.setName(newName);
        }
        String newDisplayName=ao.getDisplayName();
        if(newDisplayName!=null&& !Objects.equals(newDisplayName, old.getDisplayName())){
            old.setDisplayName(newDisplayName);
        }
        String newRemark=ao.getRemark();
        if(newRemark!=null&& !Objects.equals(newRemark, old.getRemark())){
            old.setRemark(newRemark);
        }
        String newNamespace=ao.getNamespace();
        if(newNamespace!=null&& !Objects.equals(newNamespace, old.getNamespace())){
            old.setNamespace(newNamespace);
        }
        String customFields=ao.getExt();
        if(customFields!=null&& !Objects.equals(customFields, old.getExt())){
            old.setExt(customFields);
        }
        classEntityDao.flush();
        log.debug("更新完毕{}",old);
    }

    @Override
    public ClassEntity load(Long id) throws Exception {
        ClassEntity e= classEntityDao.getById(id);
        if(e==null){
            throw new DataNotFoundException("数据不存在" );
        }
        return e;
    }

    @Override
    public List<ClassEntity> query(ClassQueryAo ao) throws Exception {
        ClassEntity probe=new ClassEntity();
        probe.setNamespace(ao.getNamespaceLike());
        probe.setName(ao.getNameLike());
        probe.setDisplayName(ao.getDisplayNameLike());
        probe.setClassType(ao.getClassType());
        Example<ClassEntity> exp=Example.of(probe,ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return classEntityDao.findAll(exp);
    }

    @Transactional
    @Override
    public void remove(Long... ids) throws Exception {
        for (Long id : ids) {
            ClassEntity e = classEntityDao.getById(id);
            if(e.getClassType()== ClassTypeEnum.CLASS_TYPE_DATA){
                dataClassFieldEntityDao.deleteByBelongClass(e);
            }else{
                enumClassFieldEntityDao.deleteByBelongClass(e);
            }
            classEntityDao.delete(e);
        }
    }

}
