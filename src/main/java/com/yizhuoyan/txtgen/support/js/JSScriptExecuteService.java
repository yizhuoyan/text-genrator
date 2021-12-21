package com.yizhuoyan.txtgen.support.js;

import javax.script.CompiledScript;
import java.util.Map;

/**
 * 脚本执行服务
 */
public interface JSScriptExecuteService {

    /**
     * 执行js脚本并获取结果
      * @param jsScript js脚本方法
     * @param arguments 执行方法参数
     * @return 执行结果
     * @throws Exception 执行过程发生的异常
     */
    Object execute(String jsScript,Map<String,?> arguments)throws Exception;

    CompiledScript compile(String jsScript)throws Exception;


}
