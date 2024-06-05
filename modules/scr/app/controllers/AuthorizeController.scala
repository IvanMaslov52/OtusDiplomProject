package controllers

import com.google.inject.Inject
import models.dao.User
import models.services.UserService
import models.utils.JwtUtil
import play.api.data.Forms.nonEmptyText
import play.api.data.{Form, Forms, Mapping}
import play.api.mvc.{Action, AnyContent, Controller}

class AuthorizeController @Inject()(userService: UserService) extends Controller {

  val regMapping: Mapping[(String, String)] = Forms.tuple(
    "username" -> nonEmptyText(minLength = 6).verifying("Username already taken", !userService.existUser(_)),
    "password" -> nonEmptyText(minLength = 6)
  )

  val regForm: Form[(String, String)] = Form(regMapping)

  val loginMapping: Mapping[(String, String)] = Forms.tuple(
    "username" -> nonEmptyText(minLength = 6).verifying("Username is not exist", userService.existUser(_) ),
    "password" -> nonEmptyText(minLength = 6)
  ) verifying("Password is wrong", field => field match {
    case (username: String, password: String) => userService.findByUsername(username).get.password == password
  })

  val loginForm: Form[(String, String)] = Form(loginMapping)


  def getLoginPage: Action[AnyContent] = Action {
    Ok(views.html.login(loginForm)).as(HTML)
  }

  def getRegistrationPage: Action[AnyContent] = Action {
    Ok(views.html.registration(regForm)).as(HTML)
  }

  def loginFormSubmit: Action[AnyContent] = Action { implicit req =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      dto => userService.findByUsername(dto._1)
        .map(x => Ok(views.html.index(dto._2)).withSession("token" -> JwtUtil.generateToken(x.username)))
        .getOrElse(Ok(views.html.login(loginForm)).as(HTML))

    )
  }

  def registrationFormSubmit: Action[AnyContent] = Action { implicit req =>
    regForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.registration(formWithErrors)),
      dto => {
        userService.addUser(User(login = dto._1, username = dto._1, password = dto._2))
        Ok(views.html.login(loginForm))
      }
    )
  }

  def logout = Action {
    Ok(views.html.login(loginForm)).as(HTML).withNewSession
  }
}
