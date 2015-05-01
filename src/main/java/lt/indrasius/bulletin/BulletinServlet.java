package lt.indrasius.bulletin;

/**
 * Created by mantas on 15.4.30.
 */

import lt.indrasius.bulletin.framework.JSPageFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class BulletinServlet extends HttpServlet
{
    private IOHandlerFactory factory = new IOHandlerFactory(new JSPageFactory());

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(0);
        IOHandler handler = factory.newHandler(asyncContext);
        request.getInputStream().setReadListener(handler);
        response.getOutputStream().setWriteListener(handler);
    }
}