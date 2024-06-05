package models.dao

import org.squeryl.{KeyedEntity, Schema, Table}
import play.api.libs.json.{Json, Reads, Writes}

case class Chat(id: Int = 0, sender: Int, receiver: Int, message: String) extends KeyedEntity[Int]

object Chat extends Schema {
  import org.squeryl.PrimitiveTypeMode._


  val chats: Table[Chat] = table[Chat]("chat")
  implicit val reads: Reads[Chat] = Json.reads[Chat]
  implicit val writes: Writes[Chat] = Json.writes[Chat]

  on(chats)(s => declare(
    s.id is (autoIncremented("chat_id_seq"))
  ))
}
