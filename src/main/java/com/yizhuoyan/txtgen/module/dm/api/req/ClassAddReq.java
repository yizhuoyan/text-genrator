package com.yizhuoyan.txtgen.module.dm.api.req;

import lombok.Data;

@Data
public class ClassAddReq {
    String name;
    String displayName;
    String namespace;
    String remark;
    String ext;
}
