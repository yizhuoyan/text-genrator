package com.yizhuoyan.txtgen.module.task.dao;
import com.yizhuoyan.txtgen.module.task.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskEntityDao extends JpaRepository<TaskEntity,Long> {
}
