package controllers

import auth.Authenticated
import com.google.inject.Inject
import models.services.{AuthService, UserService}
import play.api.data.Forms.nonEmptyText
import play.api.data.{Form, Forms, Mapping}
import play.api.libs.json.Json
import play.api.mvc._


class MainController @Inject()(authService: AuthService, userService: UserService) extends Controller {


  private val loginMapping: Mapping[(String, String)] = Forms.tuple(
    "username" -> nonEmptyText(minLength = 6).verifying("Username is not exist", userService.existUser(_)),
    "password" -> nonEmptyText(minLength = 6)
  ) verifying("Password is wrong", field => field match {
    case (username: String, password: String) => userService.findByUsername(username).get.password == password
  })

  private val loginForm: Form[(String, String)] = Form(loginMapping)


  def getMainPage: Action[AnyContent] = Authenticated { request =>
    val result = for {
      token <- request.session.get("token")
      user <- authService.getUserForToken(token)
    } yield Ok(views.html.index(user.username))
    result.getOrElse(Ok(views.html.login(loginForm)))
  }

  def getUsername: Action[AnyContent] = Authenticated { request =>
    val result = for {
      token <- request.session.get("token")
      user <- authService.getUserForToken(token)
    }  yield Ok(Json.toJson(user.username))
    result.getOrElse(NotFound)
  }
}
