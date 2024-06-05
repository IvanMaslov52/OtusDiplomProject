package models.dto

import play.api.libs.json.{Json, Reads, Writes}

case class ProjectUserDTO(user_id: Int, project_id: Int, position: String, role: String, name: String, status: String, description: String)

object ProjectUserDTO {
  implicit val reads: Reads[ProjectUserDTO] = Json.reads[ProjectUserDTO]
  implicit val writes: Writes[ProjectUserDTO] = Json.writes[ProjectUserDTO]
}
