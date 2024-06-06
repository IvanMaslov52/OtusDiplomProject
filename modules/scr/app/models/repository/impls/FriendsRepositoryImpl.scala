package models.repository.impls

import models.dao.{Friends, User}
import models.repository.FriendsRepository
import org.squeryl.Table

class FriendsRepositoryImpl extends FriendsRepository {

  override def defaultTable: Table[Friends] = Friends.friends

  override def findFriends(user_id: Int): List[User] = transaction {
    join(Friends.friends, User.users)((f, u) =>
      where(f.friends_id === user_id and f.status === "Accepted")
        select User(f.user_id, u.login, u.username, u.password)
        on (f.user_id === u.id)
    ).toList
  }

  override def findRequest(user_id: Int): List[User] = transaction {
    join(Friends.friends, User.users)((f, u) =>
      where(f.friends_id === user_id and f.status === "Waiting")
        select User(f.user_id, u.login, u.username, u.password)
        on (f.user_id === u.id)
    ).toList
  }

  override def usersExist(user_id: Int, target_id: Int): Boolean =
    transaction {
      from(defaultTable)(f =>
        where((f.user_id === user_id and f.friends_id === target_id) or (f.user_id === target_id and f.friends_id === user_id))
          select (f)
      ).headOption.isDefined
    }


}
