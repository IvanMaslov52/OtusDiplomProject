package models.repository.impls

import models.dao.{Project, ProjectUser, User}
import models.dto.{ProjectUserDTO, UserProjectDTO}
import models.repository.ProjectUserRepository
import org.squeryl.Table

import scala.language.postfixOps

class ProjectUserRepositoryImpl extends ProjectUserRepository {

  override def defaultTable: Table[ProjectUser] = ProjectUser.projectUser


  override def innerJoinWithProjects(user_id: Int): List[ProjectUserDTO] = transaction {
    join(ProjectUser.projectUser, Project.projects)((r, a) =>
      where(r.user_id === user_id)
        select ProjectUserDTO(r.user_id, r.project_id, r.position, r.role, a.name, a.status, a.description)
        on (r.project_id === a.id)
    ).toList
  }

  override def innerJoinWithProject(user_id: Int, project_id: Int): Option[ProjectUserDTO] = transaction {
    join(ProjectUser.projectUser, Project.projects)((r, a) =>
      where(r.user_id === user_id and r.project_id === project_id)
        select ProjectUserDTO(r.user_id, r.project_id, r.position, r.role, a.name, a.status, a.description)
        on (r.project_id === a.id)
    ).headOption
  }

  override def innerJoinWithUsers(project_id: Int): List[UserProjectDTO] = transaction {
    join(ProjectUser.projectUser, User.users)((r, a) =>
      where(r.project_id === project_id)
        select UserProjectDTO(r.user_id, a.username, r.position, r.role)
        on (r.user_id === a.id)
    ).toList
  }

  override def getAllUsersWithoutProject(project_id: Int): List[User] = transaction {
    join(User.users, ProjectUser.projectUser.leftOuter)((t1, t2) =>
      where(t2.map(_.project_id).isNull or t2.map(_.project_id) <> project_id)
        select (t1) on (t1.id === t2.map(_.user_id))).toList
  }

  override def innerJoinWithUser(user_id: Int, project_id: Int): Option[UserProjectDTO] = transaction {
    join(ProjectUser.projectUser, User.users)((r, a) =>
      where(r.project_id === project_id and r.user_id === user_id)
        select UserProjectDTO(r.user_id, a.username, r.position, r.role)
        on (r.user_id === a.id)
    ).headOption
  }
}
