package lt.indrasius.bulletin.dao;

import lt.indrasius.bulletin.domain.BoardId;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mantas on 15.4.27.
 */
public class SessionDAO {
    private Map<BoardId, Map<String, Boolean>> sessions = new HashMap<>();

    public void create(BoardId boardId, String username) {
        Map<String, Boolean> boardSessions = sessions.get(boardId);

        if (boardSessions == null) {
            synchronized (sessions) {
                if (sessions.get(boardId) == null) {
                    boardSessions = new ConcurrentHashMap();

                    sessions.put(boardId, boardSessions);
                }
            }
        }

        boardSessions.put(username, true);
    }

    public String[] getSessions(BoardId boardId) {
        Map<String, Boolean> boardSessions = sessions.get(boardId);
        String[] empty = new String[0];

        return boardSessions == null ? empty : boardSessions.keySet().toArray(empty);
    }
}
