package lt.indrasius.bulletin;

import lt.indrasius.bulletin.framework.JSPage;
import lt.indrasius.bulletin.framework.JSPageFactory;
import lt.indrasius.bulletin.framework.exceptions.JSPageException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mantas on 15.4.30.
 */
public class IOHandler implements ReadListener, WriteListener
{
    private final byte[] buffer = new byte[4096];
    private final AsyncContext asyncContext;
    private final ServletInputStream input;
    private final ServletOutputStream output;
    private final AtomicBoolean complete = new AtomicBoolean(false);
    private final JSPageFactory pageFactory;
    private final HttpServletRequest request;
    private final String path;
    private Future<String> result;
    private String responseContentType;
    private final ByteArrayOutputStream requestData = new ByteArrayOutputStream();

    protected IOHandler(AsyncContext asyncContext, JSPageFactory pageFactory) throws IOException
    {
        this.asyncContext = asyncContext;
        this.pageFactory = pageFactory;
        this.input = asyncContext.getRequest().getInputStream();
        this.output = asyncContext.getResponse().getOutputStream();
        this.request = (HttpServletRequest) asyncContext.getRequest();

        HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
        String path = req.getRequestURI().substring(1);

        this.path = path;
        
        Future<String> result = null;

        if (req.getMethod().equals("GET")) {
            try {
                if (path.startsWith("js/") || path.startsWith("bower_components/")) {
                    result = getFile(path);
                    responseContentType = "application/javascript";
                } else if (path.startsWith("api/")) {
                    result = pageFactory.getHandler("api").handle(request.getMethod(), path, null);
                    responseContentType = "application/json";
                } else {
                    JSPage page = pageFactory.getPage("front");

                    page.setState(path);

                    result = page.renderAsync();
                    responseContentType = "text/html";
                }
            } catch (JSPageException e) {
                e.printStackTrace();
                result = CompletableFuture.completedFuture("ERROR");
            }
        }

        this.result = result;
    }

    @Override
    public void onDataAvailable() throws IOException
    {
        while (input.isReady()) {
            requestData.write(input.read());
        }
    }

    @Override
    public void onAllDataRead() throws IOException
    {
        if (result == null) {
            try {
                String body = new String(requestData.toByteArray());
                result = pageFactory.getHandler("api").handle(request.getMethod(), path, body);
                responseContentType = "application/json";
            } catch (JSPageException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onWritePossible() throws IOException
    {
        try {
            String content = result.get(2, TimeUnit.SECONDS);
            asyncContext.getResponse().setContentType(responseContentType);

            output.write(content.getBytes());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        asyncContext.complete();
    }

    @Override
    public void onError(Throwable failure)
    {
        failure.printStackTrace();
        asyncContext.complete();
    }

    private Future<String> getFile(String path) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new String(Files.readAllBytes(Paths.get(path)));
            } catch (IOException e) {
                throw new IllegalStateException("Error", e);
            }
        });
    }
}