package models.dao

import org.squeryl.{KeyedEntity, Schema, Table}
import play.api.libs.json.{Json, Reads, Writes}

case class User(id: Int = 0, login: String = "", username: String = "", password: String = "") extends KeyedEntity[Int] {
  lazy val productUsers = User.projectsUsers.left(this)
}

object User extends Schema {

  import org.squeryl.PrimitiveTypeMode._

  implicit val reads: Reads[User] = Json.reads[User]
  implicit val writes: Writes[User] = Json.writes[User]

  val users: Table[User] = table[User]("users")
  val projects: Table[Project] = table[Project]("project")

  val projectsUsers = oneToManyRelation(users, projects).via(_.id === _.user_id)

  on(users)(s => declare(
    s.id is (autoIncremented("users_id_seq"))
  ))
}

