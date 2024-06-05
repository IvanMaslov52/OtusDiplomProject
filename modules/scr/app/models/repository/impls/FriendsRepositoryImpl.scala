package models.repository.impls

import models.dao.{Friends, User}
import models.dto.{FriendsDTO, FriendsForm}
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

  override def searchFriendsByVal(user_id: Int, username: String): List[User] = transaction {
    join(Friends.friends, User.users)((f, u) =>
      where(f.friends_id <> user_id and f.user_id <> user_id and (u.username like "%" + username + "%"))
        select User(f.user_id, u.login, u.username, u.password)
        on (f.user_id === u.id)
    ).toList
  }
}
