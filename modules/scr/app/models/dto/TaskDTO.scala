package models.dto

import play.api.libs.json.{Json, Reads, Writes}

import java.time.LocalDate

case class TaskDTO(id: Int, name: String, creator: String, project: String, executor: String, priority: String, starttime: String = "",
                   deadline: String, description: String)

object TaskDTO {
  implicit val reads: Reads[TaskDTO] = Json.reads[TaskDTO]
  implicit val writes: Writes[TaskDTO] = Json.writes[TaskDTO]
}
