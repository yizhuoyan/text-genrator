package com.yizhuoyan.txtgen.module.dm.dao;

import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import com.yizhuoyan.txtgen.module.dm.entity.DataClassFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataClassFieldEntityDao extends JpaRepository<DataClassFieldEntity,Long> {


    List<DataClassFieldEntity> findByBelongClassOrderByOrdinal(ClassEntity belongClass);

    void deleteByBelongClass(ClassEntity belongClass);
}
