package com.yizhuoyan.txtgen.support.js.impl;

import com.yizhuoyan.common.exception.ShouldNeverHappenException;
import com.yizhuoyan.txtgen.support.js.JSScriptExecuteService;
import com.yizhuoyan.txtgen.support.js.MessageCenterScriptEngineManager;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.script.*;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JSScriptExecuteServiceImpl implements JSScriptExecuteService {
    private final MessageCenterScriptEngineManager scriptEngineManager;

    @Override
    public Object execute(String jsScript, Map arguments) throws Exception {
        if(jsScript==null){
            return null;
        }
        Object result= scriptEngineManager.getJSEngine().eval(jsScript, new SimpleBindings(arguments));
        return scriptObjectMirrorArray2List(result);
    }

    private Object scriptObjectMirrorArray2List(Object obj){
        if(obj==null)return null;
        if(obj instanceof ScriptObjectMirror){
            ScriptObjectMirror scriptObjectMirror= (ScriptObjectMirror) obj;
            if(scriptObjectMirror.isArray()){
                List list=new ArrayList(scriptObjectMirror.size());
                Set<Map.Entry<String, Object>> entries = scriptObjectMirror.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    list.add(scriptObjectMirrorArray2List(entry.getValue()));
                }
                return list;
            }else {
                Map map=new HashMap(scriptObjectMirror.size(),1);
                Set<Map.Entry<String, Object>> entries = scriptObjectMirror.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    map.put(entry.getKey(),scriptObjectMirrorArray2List(entry.getValue()));
                }
                return  map;
            }
        }
        return obj;
    }

    @Override
    public CompiledScript compile(String jsScript) throws Exception {
        final ScriptEngine scriptEngine = scriptEngineManager.getJSEngine();
        if (scriptEngine instanceof Compilable) {
            return ((Compilable) scriptEngine).compile(jsScript);
        }
        throw new ShouldNeverHappenException("无法编译js脚本，js引擎不支持");
    }


    public static void main(String[] args)throws Exception {
        ScriptEngineManager scriptEngineManager=new ScriptEngineManager();
        ScriptEngine js = scriptEngineManager.getEngineByName("js");
        String script= "(function (){\n" +
                " " +
                "return {\n" +
                "    \"a\":null,\n" +
                "     \"b\":1,\n" +
                "     \"c\":1.2,\n" +
                "     \"d\":NaN,\n" +
                "     \"e\":\"abc\",\n" +
                "     \"f\":false,\n" +
                "     \"g\":undefined,\n" +
                "     \"h\":function (){},\n" +
                "     \"i\": /123/\n" +
                "};\n" +
                "    \n" +
                "})();{\n" +
                "};";
        Map map = (Map)js.eval(script);
        ScriptObjectMirror objectMirror = (ScriptObjectMirror)map.get("a");
        System.out.println(map);

    }
}
