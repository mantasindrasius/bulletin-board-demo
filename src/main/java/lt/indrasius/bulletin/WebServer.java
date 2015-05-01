package lt.indrasius.bulletin;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Created by mantas on 15.4.30.
 */
public class WebServer {
    private final int port;
    private final Server server;

    public WebServer(int port) {
        this.port = port;

        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.setConnectors(new Connector[]{connector});

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(BulletinServlet.class, "/");

        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
        server.setHandler(handlers);

        this.server = server;
    }

    public void start() throws Exception {
        server.start();
        server.setStopAtShutdown(true);
    }
}
