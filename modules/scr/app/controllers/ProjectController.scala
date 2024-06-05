package controllers

import auth.Authenticated
import com.google.inject.Inject
import models.dao.Project
import models.{ProjectId, ProjectName}
import models.dto.{AddProjectUsersDTO, SearchDTO, UpdateUserDTO, UserIdDTO}
import models.services.{AuthService, ProjectService, UserService}
import play.api.data.Forms.{nonEmptyText, number}
import play.api.data.{Form, Forms, Mapping}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

class ProjectController @Inject()(projectService: ProjectService, userService: UserService, authService: AuthService) extends Controller {


  private val addProject: Mapping[(Int, String, String)] = Forms.tuple(
    "id" -> number,
    "name" -> nonEmptyText(minLength = 4).verifying().verifying("This name is already taken", !projectService.existProject(_)),
    "description" -> nonEmptyText(minLength = 6)
  )

  private val addProjectForm: Form[(Int, String, String)] = Form(addProject)


  private val updateProject: Mapping[(Int, String, String, String)] = Forms.tuple(
    "id" -> number,
    "name" -> nonEmptyText(minLength = 4),
    "status" -> nonEmptyText(minLength = 4),
    "description" -> nonEmptyText(minLength = 6)
  ) verifying("Name already taken", field => field match {
    case (id: Int, name: String, _, _) =>
      !(projectService.getProject(id).getOrElse(Project()).name != name && projectService.existProject(name))
  })

  private val updateProjectForm: Form[(Int, String, String, String)] = Form(updateProject)

  def getProjectPage: Action[AnyContent] = Authenticated {
    Ok(views.html.project()).as(HTML)
  }

  def getAddProjectPage: Action[AnyContent] = Authenticated { rc =>
    val result = for {
      token <- rc.session.get("token")
      user <- authService.getUserForToken(token)
    } yield Ok(views.html.addProject(addProjectForm.fill((user.id, "", "")))).as(HTML)
    result.getOrElse(Ok(views.html.project()).as(HTML))
  }

  def formSubmit: Action[AnyContent] = Authenticated {
    implicit req =>
      addProjectForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.addProject(formWithErrors)),
        dto => {
          projectService.addProject(dto)
          Ok(views.html.project()).as(HTML)
        }
      )
  }

  def getProjectsByUser: Action[AnyContent] = Authenticated { rc =>
    val result = for {
      token <- rc.session.get("token")
      user <- authService.getUserForToken(token)
    } yield Ok(Json.toJson(projectService.getProjectsById(user.id))).as(JSON)
    result.getOrElse(NotFound)
  }

  def getProjectInsidePage(projectId: ProjectId): Action[AnyContent] = Authenticated { rc =>
    val result = for {
      token <- rc.session.get("token")
      user <- authService.getUserForToken(token)
      project <- projectService.getProjectById(user.id, projectId.raw)
    } yield Ok(views.html.projectInside(project))
    result.getOrElse(Ok(views.html.error("Проект не найден")))
  }

  def getParticipant(projectId: ProjectId): Action[AnyContent] = Authenticated {
    Ok(Json.toJson(projectService.getParticipant(projectId)))
  }

  def getUsersWithoutProjByVal = Authenticated(parse.json[SearchDTO]) { request =>
    userService.getAllUsersByValue(request.body).map(x => Ok(Json.toJson(x))).getOrElse(NotFound("No Users"))
  }

  def getUsersWithoutProject(projectName: ProjectName) = Authenticated {
    userService.getAllUsersWithoutProject(projectName.raw).map(x => Ok(Json.toJson(x))).getOrElse(NotFound("No Users"))
  }

  def addUserInProj = Authenticated(parse.json[AddProjectUsersDTO]) { request =>
    projectService.addUsersInProj(request.body)
    Ok
  }

  def updateUserInProj = Authenticated(parse.json[UpdateUserDTO]) { request =>
    projectService.updateUserInProj(request.body)
    Ok
  }

  def getOneUserByProj = Authenticated(parse.json[UserIdDTO]) { request =>
    projectService.getOneUserByProj(request.body)
      .map(x => Ok(Json.toJson(x)))
      .getOrElse(NotFound)
  }

  def deleteUserFromProj = Authenticated(parse.json[UserIdDTO]) { request =>
    projectService.deleteUserFromProj(request.body)
      .map(x => Ok(Json.toJson(x)))
      .getOrElse(NotFound)
  }

  def getUpdateProjectPage(projectId: ProjectId) = Authenticated {
    projectService.getProject(projectId.raw)
      .map(proj => Ok(views.html.projectUpdate(updateProjectForm.fill((proj.id, proj.name, proj.status, proj.description)))))
      .getOrElse(Ok(views.html.project()))
  }

  def updateFormSubmit = Authenticated { implicit request =>
    updateProjectForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.projectUpdate(formWithErrors)),
      dto => {
        request.session.get("token")
          .flatMap(token => authService.getUserForToken(token)
            .map { user =>
              projectService.updateProject(dto, user.id)
            })
        Ok(views.html.project())
      }
    )
  }

}
