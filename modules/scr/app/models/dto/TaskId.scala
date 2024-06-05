package models

import play.api.libs.json.{Json, Reads, Writes}
import play.api.mvc.PathBindable

case class TaskId(raw: Int)

object TaskId {
  implicit val userId: PathBindable[TaskId] = new PathBindable[TaskId] {
    override def bind(key: String, value: String): Either[String, TaskId] =
      implicitly[PathBindable[Int]].bind(key, value).right.map(TaskId(_))

    override def unbind(key: String, value: TaskId): String =
      implicitly[PathBindable[Int]].unbind(key, value.raw)
  }

  implicit val reads: Reads[TaskId] = Json.reads[TaskId]
  implicit val writes: Writes[TaskId] = Json.writes[TaskId]

}