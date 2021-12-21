package com.yizhuoyan.txtgen.module.vm.api.req;

import lombok.Data;

@Data
public class ViewFileModReq {
    String namespace;
    String name;
    String generatePath;
    String fileContent;
}
