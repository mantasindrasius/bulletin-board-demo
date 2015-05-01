package lt.indrasius.bulletin;

import lt.indrasius.bulletin.framework.JSPageFactory;

import javax.servlet.AsyncContext;
import java.io.IOException;

/**
 * Created by mantas on 15.4.30.
 */
public class IOHandlerFactory {
    private final JSPageFactory pageFactory;

    public IOHandlerFactory(JSPageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    public IOHandler newHandler(AsyncContext context) throws IOException {
        return new IOHandler(context, pageFactory);
    }
}
