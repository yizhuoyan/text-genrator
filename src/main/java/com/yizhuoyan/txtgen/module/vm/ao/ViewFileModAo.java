package com.yizhuoyan.txtgen.module.vm.ao;

import lombok.Data;

@Data
public class ViewFileModAo {
    Long id;
    String namespace;
    String name;
    String generatePath;
    String fileContent;
}
