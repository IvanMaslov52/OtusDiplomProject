package models

import models.dao.Task
import play.api.libs.json.Json

case class CommentDTO(creator: String, task: Int, description: String)

object CommentDTO {
  implicit val reads = Json.reads[CommentDTO]
  implicit val writes = Json.writes[CommentDTO]
}

