package models.services

import models.dao.User

trait AuthService {
  def getUserIdFromToken(token: String): Int

  def getUserForToken(token: String): Option[User]
}
