package lt.indrasius.bulletin.dao;

/**
 * Created by mantas on 15.4.21.
 */
public class DAOs {
    private static BoardDAO boardDAO;
    private static SessionDAO sessionDAO;

    static {
        boardDAO = new BoardDAO();
        sessionDAO = new SessionDAO();
    }

    public static BoardDAO boardDAO() {
        return boardDAO;
    }

    public static SessionDAO sessionDAO() {
        return sessionDAO;
    }
}
