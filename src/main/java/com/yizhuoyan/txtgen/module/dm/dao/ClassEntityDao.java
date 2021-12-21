package com.yizhuoyan.txtgen.module.dm.dao;

import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassEntityDao extends JpaRepository<ClassEntity,Long> {
}
