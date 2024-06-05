package models.dto

import play.api.libs.json.{Json, Reads, Writes}


case class CreateTaskDTO(name: String, creator: Int, project: Int, executor: Int, priority: String,
                         deadline: String, description: String)

object CreateTaskDTO {

  implicit val reads: Reads[CreateTaskDTO] = Json.reads[CreateTaskDTO]
  implicit val writes: Writes[CreateTaskDTO] = Json.writes[CreateTaskDTO]
}
