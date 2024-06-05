package models.repository

import models.dao.{ProjectUser, User}
import models.dto.{ProjectUserDTO, UserProjectDTO}
import org.squeryl.dsl.CompositeKey2

trait ProjectUserRepository extends CrudRepository[CompositeKey2[Int, Int], ProjectUser] {
  def innerJoinWithProjects(user_id: Int): List[ProjectUserDTO]

  def innerJoinWithProject(user_id: Int, project_id: Int): Option[ProjectUserDTO]

  def innerJoinWithUsers(project_id: Int): List[UserProjectDTO]

  def getAllUsersWithoutProject(project_id: Int): List[User]

  def innerJoinWithUser(user_id: Int, project_id: Int): Option[UserProjectDTO]
}
