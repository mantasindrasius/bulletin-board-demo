package lt.indrasius.bulletin;

import lt.indrasius.bulletin.actions.GetBoardAction;
import lt.indrasius.bulletin.actions.GetSessionsAction;
import lt.indrasius.bulletin.actions.StoreBoardAction;
import lt.indrasius.bulletin.domain.Board;
import lt.indrasius.bulletin.domain.BoardId;
import lt.indrasius.bulletin.domain.Sessions;

/**
 * Created by mantas on 15.5.2.
 */
public class ApiService {
    private GetBoardAction getBoard;
    private GetSessionsAction getSessions;
    private StoreBoardAction storeBoard;

    public ApiService(GetBoardAction getBoard,
                      GetSessionsAction getSessions,
                      StoreBoardAction storeBoard) {
        this.getBoard = getBoard;
        this.getSessions = getSessions;
        this.storeBoard = storeBoard;
    }

    public Board getBoard(String boardId) {
        return getBoard.call(boardId);
    }

    public Sessions getSessions(String boardId) {
        return getSessions.call(boardId);
    }

    public BoardId storeBoard(Board board) {
        return storeBoard.call(board);
    }
}
