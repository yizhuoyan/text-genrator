package com.yizhuoyan.txtgen.support.template.impl.beetl;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.beetl.core.ConsoleErrorHandler;
import org.beetl.core.ErrorHandler;
import org.beetl.core.GroupTemplate;
import org.beetl.core.exception.BeetlException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.PrintWriter;
import java.io.Writer;

public class ErrorInResultErrorHandler extends ConsoleErrorHandler {
    @SneakyThrows
    @Override
    public void processException(BeetlException ex, GroupTemplate groupTemplate, Writer writer) {
        writer.append("\n");
        writer.append(StrUtil.repeat('-', 100)).append("\n");
        super.processException(ex, groupTemplate, writer);
    }

    @SneakyThrows
    @Override
    protected void println(Writer w, String msg) {
        w.write(msg);
        w.write("\n");
    }

    @SneakyThrows
    @Override
    protected void print(Writer w, String msg) {
        w.write(msg);
    }

    @Override
    protected void printThrowable(Writer w, Throwable t) {
        super.printThrowable(w, t);
    }
}
