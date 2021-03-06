package lt.indrasius.bulletin.domain

import java.util.UUID

import scala.beans.BeanProperty

trait IdHelper[A] {
  def apply(id: String): A
  def create: A
}

case class BoardId(@BeanProperty id: String)

case object BoardId extends IdHelper[BoardId] {
  def create = BoardId(UUID.randomUUID().toString)
}

case class SectionId(@BeanProperty id: String)

case object SectionId extends IdHelper[SectionId] {
  def create = SectionId(UUID.randomUUID().toString)
}