package models.services

import models.dao.{Chat, User}
import models.dto.FriendsDTO

trait ProfileService {
  def getFriends(user_id: Int): List[User]

  def getRequests(user_id: Int): List[User]

  def searchFriends(user_id: Int, username: String): List[User]

  def makeRequest(user_id: Int, username: String): Unit

  def deniedRequest(user_id: Int, username: String): Unit

  def acceptedRequest(user_id: Int, username: String): Unit

  def deleteFriend(user_id: Int, username: String): Unit

  def getFriendsDTO(user_id: Int, username: String): Option[FriendsDTO]

  def getMessages(user_id: Int, username: String): Option[List[Chat]]

  def addChat(user_id: Int, username: String, text: String): Unit
}
