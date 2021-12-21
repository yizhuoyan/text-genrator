package com.yizhuoyan.txtgen.module.task.ao;

import lombok.Data;

import java.util.Map;
@Data
public class GenerateOneFileAo {
    Long dmId;
    Long templateId;
    Map  configMap;

}
