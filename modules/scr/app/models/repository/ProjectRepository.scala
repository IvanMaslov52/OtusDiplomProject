package models.repository

import models.dao.Project

trait ProjectRepository extends CrudRepository[Int, Project]{

  def findByName(name: String): Option[Project]

  def existByName(name: String): Option[Project]

}
