package com.yizhuoyan.txtgen.module.task.dto;

import lombok.Data;

import java.io.InputStream;
import java.io.Reader;

@Data
public class GenerateOneFileResult {
    String path;
    InputStream inputStream;
}
