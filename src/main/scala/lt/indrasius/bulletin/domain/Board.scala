package lt.indrasius.bulletin.domain

import scala.beans.BeanProperty

/**
 * Created by mantas on 15.4.21.
 */
case class Board(@BeanProperty title: String,
                 @BeanProperty sections: Array[Section] = Array(),
                 @BeanProperty id: BoardId = BoardId.create)
