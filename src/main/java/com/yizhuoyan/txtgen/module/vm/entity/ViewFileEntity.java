package com.yizhuoyan.txtgen.module.vm.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ViewFileEntity {
    @Id
    @GeneratedValue
    private Long id;
    String namespace;
    String name;
    String generatePath;
    String fileLocation;
    transient  String fileContent;
    ViewFileTypeEnum fileType;
}
