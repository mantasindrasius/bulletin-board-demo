package lt.indrasius.bulletin.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lt.indrasius.bulletin.ApiService;
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
    private final ObjectMapper objectMapper;
    private EventLoop eventLoop = new EventLoop();

    public JSPageFactory() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new DefaultScalaModule());

        this.objectMapper = mapper;
    }

    public JSPage getPage(String name) throws JSPageException {
        return new JSPage(getContext(name), eventLoop);
    }

    public DataHandler getHandler(String name) throws JSPageException {
        return new DataHandler(getContext(name));
    }

    private ScriptObjectMirror getContext(String name) throws JSPageException {
        ScriptEngine engine = new ScriptEngineBuilder()
                .withEventLoop(eventLoop)
                .withDOMFunctions()
                .withFileSystemFunctions()
                .withObjectMapper(objectMapper)
                .withLoadedScript("bower_components/promise-js/promise.js")
                .withLoadedScript("bower_components/dustjs-linkedin/dist/dust-full.js")
                .withLoadedScript("bower_components/dustjs-linkedin-helpers/dist/dust-helpers.js")
                .withLoadedScript("js/dust-template-loader.js")
                .withLoadedScript("js/renderer.js")
                .withLoadedScript("js/views/template-view.js")
                .withLoadedScript("js/views/add-section.js")
                .withScriptFromClassPath("server-side-init.js")
                .withScriptFromClassPath("html-page.js")
                .newEngine();

        JSWrapperGenerator codeGenerator = new JSWrapperGenerator();
        ObjectWrapper wrapper = new ObjectWrapper(engine, codeGenerator);

        try {
            ApiService service = new ApiService(
                    Actions.getBoard(),
                    Actions.getSessions(),
                    Actions.storeBoard());

            engine.put("service", wrapper.wrap(service));

            ScriptObjectMirror page = (ScriptObjectMirror) engine.eval("load('js/pages/" + name + ".js')");
            Object thiz = engine.eval("this");
            ScriptObjectMirror context = (ScriptObjectMirror) page.call(thiz);

            return context;
        } catch (ScriptException e) {
            throw new JSPageException(e.getMessage(), e);
        }
    }
}
