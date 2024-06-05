package models

import play.api.libs.json.{Json, Reads, Writes}
import play.api.mvc.PathBindable

case class UserId(raw: Int)

object UserId {
  implicit val userId: PathBindable[UserId] = new PathBindable[UserId] {
    override def bind(key: String, value: String): Either[String, UserId] =
      implicitly[PathBindable[Int]].bind(key, value).right.map(UserId(_))

    override def unbind(key: String, value: UserId): String =
      implicitly[PathBindable[Int]].unbind(key, value.raw)
  }

  implicit val reads: Reads[UserId] = Json.reads[UserId]
  implicit val writes: Writes[UserId] = Json.writes[UserId]

}
