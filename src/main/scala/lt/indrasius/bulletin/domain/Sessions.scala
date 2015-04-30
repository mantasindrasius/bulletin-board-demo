package lt.indrasius.bulletin.domain

import scala.beans.BeanProperty

/**
 * Created by mantas on 15.4.27.
 */
case class Sessions(@BeanProperty users: Array[String])
