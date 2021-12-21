package com.yizhuoyan.txtgen.module.dm.api.req;

import lombok.Data;

@Data
public class ClassQueryReq {
    String namespaceLike;
    String nameLike;
    String displayNameLike;
}
