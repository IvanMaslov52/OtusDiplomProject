package models

import play.api.libs.json.Json
import play.api.mvc.PathBindable

case class ReqDTO(raw: String)

object ReqDTO {
  implicit val reads = Json.reads[ReqDTO]
  implicit val writes = Json.writes[ReqDTO]

  implicit val projectName: PathBindable[ReqDTO] = new PathBindable[ReqDTO] {
    override def bind(key: String, value: String): Either[String, ReqDTO] =
      implicitly[PathBindable[String]].bind(key, value).right.map(ReqDTO(_))

    override def unbind(key: String, value: ReqDTO): String =
      implicitly[PathBindable[String]].unbind(key, value.raw)
  }
}
