package models.repository

import models.dao.User


trait UserRepository extends CrudRepository[Int, User] {

  def findByUsername(username: String): Option[User]

  def existByUsername(username: String): Option[User]

  def searchByUsername(username: String): List[User]

}
