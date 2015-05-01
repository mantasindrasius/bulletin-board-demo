package lt.indrasius.bulletin.framework;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lt.indrasius.nashorn.Promise;

import java.util.concurrent.Future;

/**
 * Created by mantas on 15.4.30.
 */
public class DataHandler {

    private final ScriptObjectMirror context;

    public DataHandler(ScriptObjectMirror context) {
        this.context = context;
    }

    public Future<String> handle(String method, String path, String body) {
        JSObject renderData = (JSObject) context.getMember("handle");
        JSObject json = (JSObject) context.eval("JSON.parse");
        Object objLiteral = json.call(context, body);
        Object promise = renderData.call(context, method, path, objLiteral);

        return Promise.toFuture(promise);
    }
}
