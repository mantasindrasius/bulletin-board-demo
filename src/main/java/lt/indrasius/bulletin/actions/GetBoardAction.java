package lt.indrasius.bulletin.actions;

import lt.indrasius.bulletin.dao.BoardDAO;
import lt.indrasius.bulletin.domain.Board;
import lt.indrasius.bulletin.domain.BoardId;

/**
 * Created by mantas on 15.4.21.
 */
public class GetBoardAction {

    private final BoardDAO dao;

    public GetBoardAction(BoardDAO dao) {
        this.dao = dao;
    }

    public Board call(String id) {
        return dao.get(BoardId.apply(id));
    }
}
