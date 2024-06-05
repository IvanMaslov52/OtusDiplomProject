package models.dao

import org.squeryl.{KeyedEntity, Schema, Table}
import play.api.libs.json.{Json, Reads, Writes}

case class Comment(id: Int = 0, creator: Int, task: Int, description: String) extends KeyedEntity[Int]

object Comment extends Schema{

  import org.squeryl.PrimitiveTypeMode._

  implicit val reads: Reads[Comment] = Json.reads[Comment]
  implicit val writes: Writes[Comment] = Json.writes[Comment]

  val comments: Table[Comment] = table[Comment]("comment")

  on(comments)(s => declare(
    s.id is (autoIncremented("comment_id_seq"))
  ))

}