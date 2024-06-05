package models.services.impls

import com.google.inject.Inject
import models.{ProjectId, ProjectName}
import models.dao.{Project, ProjectUser}
import models.dto.{AddProjectUsersDTO, ProjectUserDTO, UpdateUserDTO, UserIdDTO, UserProjectDTO}
import models.repository.{ProjectRepository, ProjectUserRepository}
import models.services.ProjectService

class ProjectServiceImpl @Inject()(projectRepository: ProjectRepository, projectUserRepository: ProjectUserRepository) extends ProjectService {

  override def addProject(dto: (Int, String, String)): Unit = {
    val result = projectRepository.insert(Project(name = dto._2, status = "Created", description = dto._3, user_id = dto._1))
    val projectUser = ProjectUser(dto._1, result.id, "active", "lead")
    projectUserRepository.insert(projectUser)
  }

  override def getProject(id: Int): Option[Project] = projectRepository.find(id)

  override def getProjects: List[Project] = projectRepository.list()

  override def existProject(name: String): Boolean = projectRepository.existByName(name).isDefined

  override def getProjectsById(user_id: Int): List[ProjectUserDTO] = {
    projectUserRepository.innerJoinWithProjects(user_id)
  }

  override def getProjectById(user_id: Int, project_Id: Int): Option[ProjectUserDTO] =
    projectUserRepository.innerJoinWithProject(user_id, project_Id)

  override def getParticipant(projectId: ProjectId): Option[List[UserProjectDTO]] = {
    for {
      project <- projectRepository.find(projectId.raw)
    } yield projectUserRepository.innerJoinWithUsers(project.id)
  }

  override def addUsersInProj(dto: AddProjectUsersDTO): Unit = {
    for {
      project <- projectRepository.findByName(dto.project)
    } yield projectUserRepository.insertList(dto.projectUsers.map(x => ProjectUser(x.toInt, project.id, "active", "none")))
  }

  override def updateUserInProj(dto: UpdateUserDTO): Unit = {
    for {
      project <- projectRepository.findByName(dto.project)
    } yield projectUserRepository.update(ProjectUser(dto.id, project.id, dto.position, dto.role))
  }

  override def getOneUserByProj(dto: UserIdDTO): Option[UserProjectDTO] = {
    for {
      project <- projectRepository.findByName(dto.project)
      result <- projectUserRepository.innerJoinWithUser(dto.id, project.id)
    } yield result
  }

  override def deleteUserFromProj(dto: UserIdDTO): Option[UserProjectDTO] =
    for {
      project <- projectRepository.findByName(dto.project)
      result <- projectUserRepository.innerJoinWithUser(dto.id, project.id)
    } yield {
      projectUserRepository.delete(ProjectUser(dto.id, project.id, result.position, result.role))
      result
    }

  override def updateProject(dto: (Int, String, String, String), userId: Int): Unit = {
    val project = Project(dto._1, dto._2, dto._3, dto._4, userId)
    projectRepository.update(project)
  }

  override def updateExist(id: Int, name: String): Boolean = {
    projectRepository.find(id).getOrElse(Project()).name != name && existProject(name)
  }
}
