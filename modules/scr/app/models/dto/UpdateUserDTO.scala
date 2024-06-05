package models.dto

import play.api.libs.json.Json

case class UpdateUserDTO(id: Int, role: String, position: String, project: String)

object UpdateUserDTO {
  implicit val reads = Json.reads[UpdateUserDTO]
  implicit val writes = Json.writes[UpdateUserDTO]
}
