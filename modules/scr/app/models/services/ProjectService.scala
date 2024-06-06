package models.services

import models.ProjectId
import models.dao.Project
import models.dto.{AddProjectUsersDTO, ProjectUserDTO, UpdateUserDTO, UserIdDTO, UserProjectDTO}

trait ProjectService {

  def addProject(dto: (Int, String, String)): Unit

  def getProject(id: Int): Option[Project]

  def getProjects: List[Project]

  def existProject(name: String): Boolean

  def getProjectsById(user_id: Int): List[ProjectUserDTO]

  def getProjectById(user_id: Int, project_Id: Int): Option[ProjectUserDTO]

  def getParticipant(projectId: ProjectId): Option[List[UserProjectDTO]]

  def addUsersInProj(dto: AddProjectUsersDTO): Unit

  def updateUserInProj(dto: UpdateUserDTO): Unit

  def getOneUserByProj(dto: UserIdDTO): Option[UserProjectDTO]

  def deleteUserFromProj(dto: UserIdDTO): Option[UserProjectDTO]

  def updateProject(dto: (Int, String, String, String), userId: Int): Unit

  def updateExist(id: Int, name: String): Boolean
}
