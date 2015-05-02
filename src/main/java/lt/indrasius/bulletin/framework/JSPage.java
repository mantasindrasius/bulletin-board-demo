package lt.indrasius.bulletin.framework;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lt.indrasius.nashorn.EventLoop;
import lt.indrasius.nashorn.Promise;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mantas on 15.4.21.
 */
public class JSPage {
    private final EventLoop eventLoop;
    private ScriptObjectMirror context;
    private String state;

    JSPage(ScriptObjectMirror context, EventLoop eventLoop) {
        this.context = context;
        this.eventLoop = eventLoop;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String renderDataState() {
        JSObject renderData = (JSObject) context.getMember("renderData");
        Object promise = renderData.call(context, state);
        Future<String> contentPromise = Promise.toFuture(promise);

        try {
            return contentPromise.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return "ERROR";
    }

    /*    public String renderDataState() {
        CompletableFuture result = new CompletableFuture<>();

        eventLoop.nextTick(() -> {
            JSObject renderData = (JSObject) context.getMember("renderData");
            Object promise = renderData.call(context, state);

            result.complete(promise);
        });

        try {
            Object promise = result.get(2, TimeUnit.SECONDS);
            Future<String> contentPromise = Promise.toFuture(promise);

            return contentPromise.get(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return "ERROR";
    }
*/

    public String render() {
        try {
            return renderAsync().get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return "ERROR";
    }

    public Future<String> renderAsync() {
        JSObject htmlPage = (JSObject) context.eval("new HtmlPage()");
        JSObject render = (JSObject) htmlPage.getMember("render");
        JSObject renderData = (JSObject) context.getMember("render");

        Object promise = renderData.call(context, state);

        return Promise.toFuture(render.call(htmlPage, promise));
    }
}
