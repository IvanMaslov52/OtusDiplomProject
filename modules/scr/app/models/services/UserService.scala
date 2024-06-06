package models.services

import models.UserId
import models.dao.User
import models.dto.SearchDTO

trait UserService {
  def existUser(username: String): Boolean

  def addUser(user: User): Unit

  def getUser(id: UserId): Option[User]

  def findByUsername(username: String): Option[User]

  def findById(id: Int): Option[User]

  def getAllUsersWithoutProject(projectName: String): Option[List[User]]

  def getAllUsersByValue(searchDTO: SearchDTO): Option[List[User]]

  def update(dto: (Int, String, String, String)): Unit

}
