package com.yizhuoyan.txtgen.support.template.impl.beetl;

import cn.hutool.core.util.StrUtil;
import com.yizhuoyan.txtgen.support.template.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@Component("beetl")
@Slf4j
public class BeetlTemplateServiceImpl implements TemplateService {

    private final GroupTemplate groupTemplate;

    public BeetlTemplateServiceImpl(CommonTemplateRenderFunctions functions) throws IOException {
        StringTemplateResourceLoader stringTemplateResourceLoader = new StringTemplateResourceLoader();
        Configuration configuration = Configuration.defaultConfiguration();
        groupTemplate = new GroupTemplate(stringTemplateResourceLoader, configuration);
        groupTemplate.registerFunctionPackage("$",functions);
        groupTemplate.setErrorHandler(new ErrorInResultErrorHandler());
    }

    @Override
    public String render(String templateString, Map<String, ?> model) throws Exception {
        if (StrUtil.isBlank(templateString)) {
            return templateString;
        }
        if (model == null || model.isEmpty()) {
            return templateString;
        }
        Template template = groupTemplate.getTemplate(templateString);

        template.binding(model);
        return template.render();
    }

    @Override
    public void render(String templateString, Map<String, ?> model, Writer writer) throws Exception {
        if (StrUtil.isBlank(templateString)) {
            return;
        }
        if (model == null || model.isEmpty()) {
            writer.append(templateString);
            return;
        }
        Template template = groupTemplate.getTemplate(templateString);
        template.binding(model);
        template.renderTo(writer);
    }


}
