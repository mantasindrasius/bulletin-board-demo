package lt.indrasius.bulletin.domain

import java.util.UUID

trait IdHelper[A] {
  def apply(id: String): A
  def create: A
}

case class BoardId(id: String)

case object BoardId extends IdHelper[BoardId] {
  def create = BoardId(UUID.randomUUID().toString)
}