package models.services.impls

import models.dao.User
import models.repository.UserRepository
import models.services.AuthService
import models.utils.JwtUtil

import javax.inject.Inject

class AuthServiceImpl @Inject()(userRepo: UserRepository) extends AuthService{

  override def getUserIdFromToken(token: String): Int = {
    userRepo.findByUsername(JwtUtil.getUsername(token)).get.id
  }

  override def getUserForToken(token: String): Option[User] = {
    userRepo.findByUsername(JwtUtil.getUsername(token))
  }
}
