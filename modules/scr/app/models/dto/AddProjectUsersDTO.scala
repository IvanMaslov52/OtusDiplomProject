package models.dto

import play.api.libs.json.Json


case class AddProjectUsersDTO(projectUsers: List[String], project: String)

object AddProjectUsersDTO {
  implicit val reads = Json.reads[AddProjectUsersDTO]
  implicit val writes = Json.writes[AddProjectUsersDTO]
}

