package models.dto

import play.api.libs.json.Json

case class UserIdDTO(id: Int, project: String)

object UserIdDTO {
  implicit val reads = Json.reads[UserIdDTO]
  implicit val writes = Json.writes[UserIdDTO]
}

