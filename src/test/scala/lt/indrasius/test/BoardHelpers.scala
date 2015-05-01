package lt.indrasius.test

import lt.indrasius.bulletin.dao.DAOs
import lt.indrasius.bulletin.domain.{BoardId, Board}

/**
 * Created by mantas on 15.4.30.
 */
object BoardHelpers {
  val boardDao = DAOs.boardDAO()
  val sessionsDAO = DAOs.sessionDAO()

  def givenBoardExists(board: Board) = {
    boardDao.store(board)
    board.getId
  }

  def givenBoardIsVisitedBy(boardId: BoardId, users: Seq[String]) = {
    users foreach { sessionsDAO.create(boardId, _) }
  }
}
