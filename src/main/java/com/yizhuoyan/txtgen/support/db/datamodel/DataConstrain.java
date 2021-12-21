package com.yizhuoyan.txtgen.support.db.datamodel;

import lombok.Data;

import java.text.NumberFormat;

/**
 * 数据约束
 */
@Data
public class DataConstrain {

    Boolean required;

    Boolean unique;

    String regexPattern;
    //最小字符长度
    Integer minCharLength;
    //最大字符长度
    Integer maxCharLength;
    //小数位数
    Integer fractionDigits;
    //整数位数
    Integer integerDigits;
}