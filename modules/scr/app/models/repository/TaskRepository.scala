package models.repository

import models.dao.Task


trait TaskRepository extends CrudRepository[Int, Task] {
  def findTasksByUser(userId: Int): List[Task]

  def findTaskByString(req: String): List[Task]
}
