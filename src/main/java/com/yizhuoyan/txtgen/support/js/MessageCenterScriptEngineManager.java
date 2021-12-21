package com.yizhuoyan.txtgen.support.js;

import cn.hutool.core.util.StrUtil;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 封装一层，避免和其他模块冲突
 */
@Component
public class MessageCenterScriptEngineManager {

    private final static ScriptEngineManager scriptEngineManager=new ScriptEngineManager();

    static {
        System.setProperty("nashorn.args","--no-deprecation-warning");
        scriptEngineManager.put("$", new CommonFunction());
    }

    public @NonNull     ScriptEngine getJSEngine(){
        return scriptEngineManager.getEngineByName("js");
    }


}
