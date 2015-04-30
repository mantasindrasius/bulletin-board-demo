package lt.indrasius.bulletin.domain

import scala.beans.BeanProperty

/**
 * Created by mantas on 15.4.21.
 */
case class Section(@BeanProperty title: String,
                   @BeanProperty description: String)
