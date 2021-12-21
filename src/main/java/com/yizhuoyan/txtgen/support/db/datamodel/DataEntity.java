package com.yizhuoyan.txtgen.support.db.datamodel;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 数据实体  核心数据模型
 *
 *
 */
@Data
public class DataEntity implements Serializable {

    Long id;
    /**命名空间，可为空*/
    String namespace;
    /**实体名称*/
    String name;
    /**显示名称*/
    String displayName;

    String remark;

    LocalDateTime createTime;


    List<DataField<?>> fields= Collections.EMPTY_LIST;

    /**父实体id*/
    Long parentId;
    /**父实体*/
    DataEntity parent;


}