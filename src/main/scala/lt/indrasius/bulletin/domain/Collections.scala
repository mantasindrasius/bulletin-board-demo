package lt.indrasius.bulletin.domain

/**
 * Created by mantas on 15.4.27.
 */
object Collections {
  def list[A](items: A*): List[A] = List(items:_*)
}
