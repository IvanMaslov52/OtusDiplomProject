package models.services.impls

import models.{ProjectName, UserId}
import models.dao.User
import models.dto.{SearchDTO, UserIdDTO, UserProjectDTO}
import models.repository.{ProjectRepository, ProjectUserRepository, UserRepository}
import models.services.UserService

import javax.inject.Inject

class UserServiceImpl @Inject()(userRepo: UserRepository, projectUserRepository: ProjectUserRepository, projectRepository: ProjectRepository) extends UserService {

  override def existUser(username: String): Boolean = userRepo.existByUsername(username).isDefined

  override def addUser(user: User): Unit = userRepo.insert(user)

  override def getUser(id: UserId): Option[User] = userRepo.find(id.raw)

  override def findByUsername(username: String): Option[User] = userRepo.findByUsername(username)

  override def findById(id: Int): Option[User] = userRepo.find(id)

  override def getAllUsersWithoutProject(projectName: String): Option[List[User]] = {
    for {
      projectId <- projectRepository.findByName(projectName)
    } yield projectUserRepository.getAllUsersWithoutProject(projectId.id)
  }

  override def getAllUsersByValue(searchDTO: SearchDTO): Option[List[User]] = {
    for {
      project <- projectRepository.findByName(searchDTO.project)
    } yield projectUserRepository.getAllUsersWithoutProject(project.id).filter(_.username.startsWith(searchDTO.value))
  }

  override def update(dto: (Int, String, String, String)): Unit = {
    userRepo.update(User(dto._1, dto._2, dto._3, dto._4))
  }

}
