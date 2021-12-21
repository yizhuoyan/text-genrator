package com.yizhuoyan.txtgen.support.template;

import java.io.Writer;
import java.util.Map;

public interface TemplateService {

     String render(String template, Map<String,?> model)throws Exception;

     void render(String template, Map<String,?> model, Writer writer)throws Exception;
}
