package models.dto

import play.api.libs.json.Json

case class UserProjectDTO(user_id: Int, username: String, role: String, position: String)

object UserProjectDTO {
  implicit val reads = Json.reads[UserProjectDTO]
  implicit val writes = Json.writes[UserProjectDTO]
}
