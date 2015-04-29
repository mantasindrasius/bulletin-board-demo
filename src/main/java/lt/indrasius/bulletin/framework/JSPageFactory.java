package lt.indrasius.bulletin.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lt.indrasius.bulletin.actions.Actions;
import lt.indrasius.bulletin.framework.exceptions.JSPageException;
import lt.indrasius.nashorn.EventLoop;
import lt.indrasius.nashorn.ScriptEngineBuilder;
import lt.indrasius.nashorn.jsify.JSWrapperGenerator;
import lt.indrasius.nashorn.jsify.ObjectWrapper;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Created by mantas on 15.4.21.
 */
public class JSPageFactory {
    public JSPage getPage(String name) throws JSPageException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new DefaultScalaModule());

        ScriptEngine engine = new ScriptEngineBuilder()
                .withEventLoop(new EventLoop())
                .withDOMFunctions()
                .withObjectMapper(mapper)
                .withLoadedScript("bower_components/promise-js/promise.js")
                .newEngine();

        JSWrapperGenerator codeGenerator = new JSWrapperGenerator();
        ObjectWrapper wrapper = new ObjectWrapper(engine, codeGenerator);

        try {
            engine.put("getBoard", wrapper.wrap(Actions.getBoard()));
            engine.put("getSessions", wrapper.wrap(Actions.getSessions()));

            ScriptObjectMirror page = (ScriptObjectMirror) engine.eval("load('js/pages/" + name + ".js')");
            Object thiz = engine.eval("this");
            ScriptObjectMirror context = (ScriptObjectMirror) page.call(thiz);

            return new JSPage(context);
        } catch (ScriptException e) {
            throw new JSPageException(e.getMessage(), e);
        }
    }
}
