package models.dto

import play.api.libs.json.{Json, Reads, Writes}

case class TaskUpdateDTO(id: Int, name: String, creator: Int, project: Int, executor: Int, priority: String, starttime: String,
                         deadline: String, description: String)

object TaskUpdateDTO {
  implicit val reads: Reads[TaskUpdateDTO] = Json.reads[TaskUpdateDTO]
  implicit val writes: Writes[TaskUpdateDTO] = Json.writes[TaskUpdateDTO]
}

