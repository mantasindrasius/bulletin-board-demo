package lt.indrasius.bulletin.actions;

import lt.indrasius.bulletin.dao.BoardDAO;
import lt.indrasius.bulletin.domain.Board;
import lt.indrasius.bulletin.domain.BoardId;

/**
 * Created by mantas on 15.4.30.
 */
public class StoreBoardAction {
    private final BoardDAO dao;

    public StoreBoardAction(BoardDAO dao) {
        this.dao = dao;
    }

    public BoardId call(Board board) {
        dao.store(board);

        return board.getId();
    }
}
