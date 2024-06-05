package models

import play.api.libs.json.{Json, Reads, Writes}
import play.api.mvc.PathBindable

case class ProjectId(raw: Int)

object ProjectId {
  implicit val projectID: PathBindable[ProjectId] = new PathBindable[ProjectId] {
    override def bind(key: String, value: String): Either[String, ProjectId] =
      implicitly[PathBindable[Int]].bind(key, value).right.map(ProjectId(_))

    override def unbind(key: String, value: ProjectId): String =
      implicitly[PathBindable[Int]].unbind(key, value.raw)
  }

  implicit val reads: Reads[ProjectId] = Json.reads[ProjectId]
  implicit val writes: Writes[ProjectId] = Json.writes[ProjectId]

}
