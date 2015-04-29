package lt.indrasius.bulletin.domain

import scala.beans.BeanProperty

/**
 * Created by mantas on 15.4.21.
 */
@BeanProperty
case class Board(title: String,
                 sections: List[Section] = Nil,
                 id: BoardId = BoardId.create)
