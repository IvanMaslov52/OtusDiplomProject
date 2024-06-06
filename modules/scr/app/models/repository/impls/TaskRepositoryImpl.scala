package models.repository.impls

import models.dao.Task
import models.repository.TaskRepository
import org.squeryl.Table



class TaskRepositoryImpl extends TaskRepository {

  override def defaultTable: Table[Task] = Task.tasks

  override def findTasksByUser(userId: Int): List[Task] =
    transaction {
      from(defaultTable)(task => where(task.executor === userId) select task).toList
    }

  override def findTaskByString(req: String): List[Task] = transaction {
    from(defaultTable)(task => where((task.description like s"%$req%") or (task.name like s"%$req%")) select task).toList
  }
}
