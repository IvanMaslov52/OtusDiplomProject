package models.services.impls

import models.dao.{Task, User}
import models.dto.{CreateTaskDTO, TaskDTO, TaskUpdateDTO}
import models.repository.{ProjectRepository, TaskRepository, UserRepository}
import models.services.TaskService

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class TaskServiceImpl @Inject()(taskRepository: TaskRepository, projectRepository: ProjectRepository, userRepository: UserRepository) extends TaskService {
  val formatterInput = new SimpleDateFormat("yyyy-MM-dd")

  override def getAllTasksByUser(user: User): List[TaskDTO] = {
    taskRepository.findTasksByUser(user.id).flatMap { task =>
      val result = for {
        creator <- userRepository.find(task.creator)
        project <- projectRepository.find(task.project)
        executor <- userRepository.find(task.executor)
      } yield TaskDTO(task.id, task.name, creator.username, project.name, executor.username, task.priority,
        formatterInput.format(task.starttime), formatterInput.format(task.deadline), task.description)
      result.flatMap(Some(_))
    }
  }

  override def createTask(dto: CreateTaskDTO): Unit = {
    val task = Task(name = dto.name, creator = dto.creator, project = dto.project, executor = dto.executor, priority = dto.priority,
      starttime = new Date(System.currentTimeMillis()), deadline = formatterInput.parse(dto.deadline), description = dto.description)
    taskRepository.insert(task)
  }

  override def getTaskById(taskId: Int): Option[Task] =
    taskRepository.find(taskId)

  override def getAllInfoAboutTaskById(taskId: Int): Option[TaskDTO] = {
    for {
      task <- taskRepository.find(taskId)
      creator <- userRepository.find(task.creator)
      project <- projectRepository.find(task.project)
      executor <- userRepository.find(task.executor)
    } yield TaskDTO(task.id, task.name, creator.username, project.name, executor.username, task.priority,
      formatterInput.format(task.starttime), formatterInput.format(task.deadline), task.description)
  }

  override def updateTask(taskId: Int, dto: TaskUpdateDTO): Unit = {
    for {
      task <- taskRepository.find(taskId)
    } yield taskRepository.update(Task(task.id, dto.name, dto.creator, dto.project, dto.executor, dto.priority,
      formatterInput.parse(dto.starttime), formatterInput.parse(dto.deadline), dto.description))
  }

  override def findTaskByString(req: String): List[Task] = taskRepository.findTaskByString(req)
}