package models.repository.impls

import models.dao.Project
import models.repository.ProjectRepository
import org.squeryl.Table

class ProjectRepositoryImpl extends ProjectRepository{

  override def defaultTable: Table[Project] = Project.projects

  override def findByName(name: String): Option[Project] =
    transaction(from(defaultTable)(r => where(r.name === name) select (r)).headOption)

  override def existByName(name: String): Option[Project] =
    transaction(from(defaultTable)(r => where(r.name === name) select (r)).headOption)
}
