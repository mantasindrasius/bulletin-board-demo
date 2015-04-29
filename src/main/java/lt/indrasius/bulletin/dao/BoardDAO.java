package lt.indrasius.bulletin.dao;

import lt.indrasius.bulletin.domain.Board;
import lt.indrasius.bulletin.domain.BoardId;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by mantas on 15.4.21.
 */
public class BoardDAO {
    private ConcurrentMap<BoardId, Board> boards = new ConcurrentHashMap<>();

    public void store(Board board) {
        boards.put(board.id(), board);
    }

    public Board get(BoardId id) {
        return boards.get(id);
    }
}
