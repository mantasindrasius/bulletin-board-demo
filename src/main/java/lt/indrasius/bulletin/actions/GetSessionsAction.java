package lt.indrasius.bulletin.actions;

import lt.indrasius.bulletin.dao.SessionDAO;
import lt.indrasius.bulletin.domain.BoardId;
import lt.indrasius.bulletin.domain.Sessions;

/**
 * Created by mantas on 15.4.27.
 */
public class GetSessionsAction {
    private SessionDAO sessionDAO;

    public GetSessionsAction(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    public Sessions call(String boardId) {
        String[] sessions = this.sessionDAO.getSessions(BoardId.apply(boardId));

        return Sessions.apply(sessions);
    }
}
