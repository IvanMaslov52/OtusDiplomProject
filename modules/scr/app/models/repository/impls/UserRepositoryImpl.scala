package models.repository.impls

import models.dao.User
import models.repository.UserRepository
import org.squeryl.Table

class UserRepositoryImpl extends UserRepository {
  override def defaultTable: Table[User] = User.users

  override def findByUsername(username: String): Option[User] =
    transaction(from(defaultTable)(r => where(r.username === username) select (r)).headOption)

  override def existByUsername(username: String): Option[User] =
    transaction(from(defaultTable)(r => where(r.username === username) select (r)).headOption)

  override def searchByUsername(username: String): List[User] =
    transaction(from(defaultTable)(r => where(r.username like "%" + username + "%") select (r)).toList)
}
