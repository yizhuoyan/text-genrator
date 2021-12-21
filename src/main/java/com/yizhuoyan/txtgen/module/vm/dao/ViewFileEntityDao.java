package com.yizhuoyan.txtgen.module.vm.dao;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public interface ViewFileEntityDao extends JpaRepository<ViewFileEntity,Long>, JpaSpecificationExecutor {



    default  List<ViewFileEntity> listTemplateViewFile(){
        return this.findAll(new Specification<ViewFileEntity>() {
            @Override
            public Predicate toPredicate(Root<ViewFileEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate fileType1 = criteriaBuilder.notEqual(root.get("fileType"), ViewFileTypeEnum.JS);
                Predicate fileType2 = criteriaBuilder.notEqual(root.get("fileType"), ViewFileTypeEnum.VM);
                return  criteriaBuilder.and(fileType1,fileType2);
            }
        });
    }

}
