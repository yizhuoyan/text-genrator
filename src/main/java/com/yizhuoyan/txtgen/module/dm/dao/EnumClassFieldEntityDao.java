package com.yizhuoyan.txtgen.module.dm.dao;

import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import com.yizhuoyan.txtgen.module.dm.entity.EnumClassFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnumClassFieldEntityDao extends JpaRepository<EnumClassFieldEntity,Long> {
    void deleteByBelongClass(ClassEntity belongClass);
    List<EnumClassFieldEntity> findByBelongClassOrderByOrdinal(ClassEntity belongClass);
}
