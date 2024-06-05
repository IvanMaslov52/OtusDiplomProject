package models.dao

import org.squeryl.{KeyedEntity, Schema, Table}
import play.api.libs.json.{Json, Reads, Writes}

case class Project(id: Int = 0, name: String = "", status: String = "", description: String = "", user_id: Int = 0)
  extends KeyedEntity[Int]

object Project extends Schema {

  import org.squeryl.PrimitiveTypeMode._

  implicit val reads: Reads[Project] = Json.reads[Project]
  implicit val writes: Writes[Project] = Json.writes[Project]

  val projects: Table[Project] = table[Project]("project")

  on(projects)(s => declare(
    s.id is (autoIncremented("project_id_seq"))
  ))
}

