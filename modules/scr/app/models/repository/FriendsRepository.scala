package models.repository

import models.dao.{Friends, User}
import models.dto.{FriendsDTO, FriendsForm}
import org.squeryl.dsl.CompositeKey2

trait FriendsRepository extends CrudRepository[CompositeKey2[Int, Int], Friends] {

  def findFriends(user_id: Int): List[User]

  def findRequest(user_id: Int): List[User]

  def usersExist(user_id: Int, target_id: Int): Boolean

}