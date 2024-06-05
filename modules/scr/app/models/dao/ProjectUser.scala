package models.dao

import org.squeryl.PrimitiveTypeMode.compositeKey
import org.squeryl.dsl.CompositeKey2
import org.squeryl.{KeyedEntity, Schema, Table}

case class ProjectUser(user_id: Int, project_id: Int, position: String, role: String) extends KeyedEntity[CompositeKey2[Int, Int]] {
  def id: CompositeKey2[Int, Int] = compositeKey(user_id, project_id)
}

object ProjectUser extends Schema {
  val projectUser: Table[ProjectUser] = table[ProjectUser]("users_project")
  val users: Table[User] = table[User]("users")
  val projects: Table[Project] = table[Project]("project")
}
