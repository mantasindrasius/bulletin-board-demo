package lt.indrasius.bulletin;

/**
 * Created by mantas on 15.4.30.
 */
public class ServerApp {
    public static void main(String [] args) throws Exception {
        new WebServer(Integer.parseInt(args[0])).start();
    }
}
