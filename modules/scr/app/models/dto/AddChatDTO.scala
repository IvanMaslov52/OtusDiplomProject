package models.dto

import play.api.libs.json.Json

case class AddChatDTO(receiver: String, text: String)

object AddChatDTO {
  implicit val reads = Json.reads[AddChatDTO]
  implicit val writes = Json.writes[AddChatDTO]
}
