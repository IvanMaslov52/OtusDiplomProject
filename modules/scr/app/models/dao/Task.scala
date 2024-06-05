package models.dao

import org.squeryl.{KeyedEntity, Schema, Table}
import play.api.libs.json.Json

import java.util.Date

case class Task(id: Int = 0, name: String = "", creator: Int = 0, project: Int = 0, executor: Int = 0, priority: String = "", starttime: Date,
                deadline: Date, description: String = "") extends KeyedEntity[Int]

object Task extends Schema {

  import org.squeryl.PrimitiveTypeMode._

  val tasks: Table[Task] = table[Task]("tasks")
  implicit val reads = Json.reads[Task]
  implicit val writes = Json.writes[Task]


  on(tasks)(task => declare(
    task.id is (autoIncremented("tasks_id_seq"))
  ))
}
