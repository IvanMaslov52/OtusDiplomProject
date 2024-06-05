package models.services

import models.dao.{Task, User}
import models.dto.{CreateTaskDTO, TaskDTO, TaskUpdateDTO}

trait TaskService {
  def getAllTasksByUser(user: User): List[TaskDTO]

  def createTask(createTaskDTO: CreateTaskDTO): Unit

  def getTaskById(taskId: Int): Option[Task]

  def getAllInfoAboutTaskById(taskId: Int): Option[TaskDTO]

  def updateTask(taskId: Int,dto: TaskUpdateDTO):Unit

  def findTaskByString(req: String): List[TaskDTO]
}
