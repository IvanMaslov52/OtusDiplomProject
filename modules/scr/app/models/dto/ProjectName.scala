package models

import play.api.libs.json.{Json, Reads, Writes}
import play.api.mvc.PathBindable

case class ProjectName (raw: String)
object ProjectName {
  implicit val reads: Reads[ProjectName] = Json.reads[ProjectName]
  implicit val writes: Writes[ProjectName] = Json.writes[ProjectName]

  implicit val projectName: PathBindable[ProjectName] = new PathBindable[ProjectName] {
    override def bind(key: String, value: String): Either[String, ProjectName] =
      implicitly[PathBindable[String]].bind(key, value).right.map(ProjectName(_))

    override def unbind(key: String, value: ProjectName): String =
      implicitly[PathBindable[String]].unbind(key, value.raw)
  }
}
