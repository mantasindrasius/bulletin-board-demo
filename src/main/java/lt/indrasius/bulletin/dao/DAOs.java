package lt.indrasius.bulletin.dao;

/**
 * Created by mantas on 15.4.21.
 */
public class DAOs {
    private static BoardDAO boardDAO;
    private static SessionDAO sessionDAO;

    public static BoardDAO boardDAO() {
        if (boardDAO == null) {
            boardDAO = new BoardDAO();
        }

        return boardDAO;
    }

    public static SessionDAO sessionDAO() {
        if (sessionDAO == null) {
            sessionDAO = new SessionDAO();
        }

        return sessionDAO;
    }
}
