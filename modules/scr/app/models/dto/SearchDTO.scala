package models.dto

import play.api.libs.json.Json

case class SearchDTO(value: String, project: String)

object SearchDTO{
  implicit val reads = Json.reads[SearchDTO]
  implicit val writes = Json.writes[SearchDTO]
}
