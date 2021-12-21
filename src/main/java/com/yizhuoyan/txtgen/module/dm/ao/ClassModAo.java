package com.yizhuoyan.txtgen.module.dm.ao;

import com.yizhuoyan.txtgen.module.dm.api.req.ClassModReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClassModAo extends ClassModReq {
    Long id;
}
