package models.services.impls

import com.google.inject.Inject
import models.dao.{Chat, Friends, User}
import models.dto.FriendsDTO
import models.repository.{ChatRepository, FriendsRepository, UserRepository}
import models.services.ProfileService

class ProfileServiceImpl @Inject()(friendsRepository: FriendsRepository, userRepository: UserRepository, chatRepository: ChatRepository) extends ProfileService {

  override def getFriends(user_id: Int): List[User] = {
    friendsRepository.findFriends(user_id)
  }

  override def getRequests(user_id: Int): List[User] = {
    friendsRepository.findRequest(user_id)
  }

  override def searchFriends(user_id: Int, username: String): List[User] = {
    val result = userRepository.searchByUsername(username).filter(_.id != user_id)
    result.filterNot(friendsRepository.searchFriendsByVal(user_id: Int, username: String).contains)
  }

  override def makeRequest(user_id: Int, username: String): Unit = {
    for {
      user <- userRepository.findByUsername(username)
    } yield friendsRepository.insert(Friends(user_id = user_id, friends_id = user.id, status = "Waiting"))
  }

  override def deniedRequest(user_id: Int, username: String): Unit = {

    for {
      user <- userRepository.findByUsername(username)
    } yield
      friendsRepository.delete(Friends(friends_id = user_id, user_id = user.id, status = "Waiting"))
  }


  override def acceptedRequest(user_id: Int, username: String): Unit = for {
    user <- userRepository.findByUsername(username)
  } yield {
    friendsRepository.update(Friends(friends_id = user_id, user_id = user.id, status = "Accepted"))
    friendsRepository.insert(Friends(user_id = user_id, friends_id = user.id, status = "Accepted"))
  }


  override def deleteFriend(user_id: Int, username: String): Unit = for {
    user <- userRepository.findByUsername(username)
  } yield {
    friendsRepository.delete(Friends(friends_id = user_id, user_id = user.id, status = "Accepted"))
    friendsRepository.delete(Friends(user_id = user_id, friends_id = user.id, status = "Accepted"))
  }

  override def getFriendsDTO(user_id: Int, username: String): Option[FriendsDTO] = {
    for {
      sender <- userRepository.find(user_id)
      receiver <- userRepository.findByUsername(username)
    } yield FriendsDTO(sender.id, sender.username, receiver.id, receiver.username)
  }

  override def getMessages(user_id: Int, username: String): Option[List[Chat]] = {
    for {
      receiver <- userRepository.findByUsername(username)
    } yield chatRepository.find(user_id, receiver.id)
  }


  override def addChat(user_id: Int, username: String, text: String): Unit = {
    for {
      receiver <- userRepository.findByUsername(username)
    } yield chatRepository.insert(Chat(sender = user_id, receiver = receiver.id, message = text))
  }

}
