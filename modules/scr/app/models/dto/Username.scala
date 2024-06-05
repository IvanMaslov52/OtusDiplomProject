package models

import play.api.libs.json.{Json, Reads, Writes}
import play.api.mvc.PathBindable

case class Username(raw: String)

object Username {
  implicit val reads: Reads[Username] = Json.reads[Username]
  implicit val writes: Writes[Username] = Json.writes[Username]

  implicit val projectName: PathBindable[Username] = new PathBindable[Username] {
    override def bind(key: String, value: String): Either[String, Username] =
      implicitly[PathBindable[String]].bind(key, value).right.map(Username(_))

    override def unbind(key: String, value: Username): String =
      implicitly[PathBindable[String]].unbind(key, value.raw)
  }
}