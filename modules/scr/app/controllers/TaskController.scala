package controllers

import auth.Authenticated
import com.google.inject.Inject
import controllers.Assets.Ok
import models.{CommentDTO, ProjectId, ReqDTO, TaskId}
import models.dao.Task
import models.dto.{CreateTaskDTO, TaskUpdateDTO}
import models.services.{AuthService, CommentService, TaskService}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class TaskController @Inject()(authService: AuthService, taskService: TaskService, commentService: CommentService) extends Controller {
  def getTasksPage = Authenticated { request =>
    Ok(views.html.tasks())
  }

  def getAllTasksByUser = Authenticated { request =>
    val result = for {
      token <- request.session.get("token")
      user <- authService.getUserForToken(token)
    } yield {
      Ok(Json.toJson(taskService.getAllTasksByUser(user))).as(JSON)
    }
    result.getOrElse(Forbidden)
  }

  def getTaskById(taskId: TaskId) = Authenticated { request =>
    val result = for {
      token <- request.session.get("token")
      user <- authService.getUserForToken(token)
    } yield Ok(Json.toJson(taskService.getAllTasksByUser(user)))
    result.getOrElse(Forbidden)
  }

  def getCreateTaskPage(projectId: ProjectId) = Authenticated { request =>
    val result = for {
      token <- request.session.get("token")
      user <- authService.getUserForToken(token)
    } yield Ok(views.html.taskCreate(user, projectId.raw))
    result.getOrElse(Ok(views.html.error("Пользователь не найден")))
  }

  def createTask = Authenticated(parse.json[CreateTaskDTO]) { request =>
    taskService.createTask(request.body)
    Ok
  }

  def getTaskUpdatePage(taskId: TaskId) = Authenticated {
    val result = for {
      task <- taskService.getTaskById(taskId.raw)
    } yield Ok(views.html.taskUpdate(task))
    result.getOrElse(Ok(views.html.error("Задача не найдена")))
  }

  def getTaskPage(taskId: TaskId) = Authenticated {
    val result = for {
      task <- taskService.getAllInfoAboutTaskById(taskId.raw)
    } yield Ok(views.html.task(task))
    result.getOrElse(NotFound)
  }

  def updateTask(taskId: TaskId) = Authenticated(parse.json[TaskUpdateDTO]) { request =>
    taskService.updateTask(taskId.raw, request.body)
    Ok
  }

  def findTaskByValue(dto: ReqDTO) = Authenticated {
    Ok(Json.toJson(taskService.findTaskByString(dto.raw)))
  }

  def getComments(taskId: TaskId) = Authenticated {
    Ok(Json.toJson(commentService.findCommentByTask(taskId.raw)))
  }

  def saveComments = Authenticated(parse.json[CommentDTO]) { request =>
    commentService.saveComment(request.body)
    Ok
  }


}
