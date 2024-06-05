package controllers

import auth.Authenticated
import com.google.inject.Inject
import models.Username
import models.dao.User
import models.dto.{AddChatDTO, FriendsForm}
import models.services.{AuthService, ProfileService, UserService}
import models.utils.JwtUtil
import play.api.data.{Form, Forms, Mapping}
import play.api.data.Forms.{mapping, nonEmptyText, number}
import play.api.libs.json.Json
import play.api.mvc.Controller

class ProfileController @Inject()(authService: AuthService, userService: UserService, profileService: ProfileService) extends Controller {

  private val profileMapping: Mapping[(Int, String, String, String)] = Forms.tuple(
    "id" -> number,
    "login" -> nonEmptyText(minLength = 6),
    "username" -> nonEmptyText(minLength = 6),
    "password" -> nonEmptyText(minLength = 6)
  ) verifying("Username already taken", field => field match {
    case (id: Int, _, username: String, _) =>
      !(userService.findById(id).getOrElse(User()).username != username && userService.existUser(username))
  })
  private val profileForm: Form[(Int, String, String, String)] = Form(profileMapping)


  private val friendForm: Form[FriendsForm] = Form(mapping(
    "who" -> nonEmptyText
  )(FriendsForm.apply)(FriendsForm.unapply))

  def getProfilePage = Authenticated { request =>
    val result = for {
      token <- request.session.get("token")
      user <- authService.getUserForToken(token)
    } yield Ok(views.html.profile(user, profileForm.fill(user.id, user.login, user.username, user.password)))
    result.getOrElse(Forbidden)
  }

  def getFriendsPage = Authenticated { request =>
    val result = for {
      token <- request.session.get("token")
      user <- authService.getUserForToken(token)
    } yield Ok(views.html.friends(user, friendForm))
    result.getOrElse(Forbidden)
  }

  def formSubmit = Authenticated { implicit req =>
    val result = req.session.get("token")
      .flatMap(token => authService.getUserForToken(token))
      .getOrElse(User())

    profileForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.profile(result, formWithErrors)),
      dto => {
        userService.update(dto)
        Ok(views.html.index(dto._3)).as(HTML).withSession("token" -> JwtUtil.generateToken(dto._3))
      }
    )
  }

  def findFriends = Authenticated { request =>
    val result = for {
      token <- request.session.get("token")
      user <- authService.getUserForToken(token)
    } yield Ok(Json.toJson(profileService.getFriends(user.id)))
    result.getOrElse(NotFound)
  }

  def findRequest = Authenticated { request =>
    val result = for {
      token <- request.session.get("token")
      user <- authService.getUserForToken(token)
    } yield Ok(Json.toJson(profileService.getRequests(user.id)))
    result.getOrElse(NotFound)
  }

  def searchFriends(username: Username) = Authenticated { request =>
    val result = for {
      token <- request.session.get("token")
      user <- authService.getUserForToken(token)
    } yield Ok(Json.toJson(profileService.searchFriends(user.id, username.raw)))
    result.getOrElse(NotFound)
  }

  def friendsFormSubmit = Authenticated { implicit req =>
    val result = req.session.get("token")
      .flatMap(token => authService.getUserForToken(token))
      .getOrElse(User())
    friendForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.friends(result, formWithErrors)),
      dto => {
        profileService.makeRequest(result.id, dto.who)
        Ok(views.html.friends(result, friendForm))
      }
    )
  }

  def deniedRequest = Authenticated { implicit req =>
    val result = for {
      token <- req.session.get("token")
      user <- authService.getUserForToken(token)
    } yield {
      req.body.asFormUrlEncoded.get("denied").foreach(profileService.deniedRequest(user.id, _))
      Ok(views.html.friends(user, friendForm))
    }
    result.getOrElse(Forbidden)
  }

  def acceptedRequest = Authenticated { implicit req =>
    val result = for {
      token <- req.session.get("token")
      user <- authService.getUserForToken(token)
    } yield {
      req.body.asFormUrlEncoded.get("accepted").foreach(profileService.acceptedRequest(user.id, _))
      Ok(views.html.friends(user, friendForm))
    }
    result.getOrElse(Forbidden)
  }

  def deleteFriend = Authenticated { implicit req =>
    val result = for {
      token <- req.session.get("token")
      user <- authService.getUserForToken(token)
    } yield {
      req.body.asFormUrlEncoded.get("username").foreach(profileService.deleteFriend(user.id, _))
      Ok(views.html.friends(user, friendForm))
    }
    result.getOrElse(Forbidden)
  }

  def convChat(username: Username) = Authenticated { implicit req =>
    val result = for {
      token <- req.session.get("token")
      user <- authService.getUserForToken(token)
      dto <- profileService.getFriendsDTO(user.id, username.raw)
    } yield {
      Ok(views.html.chat(dto))
    }
    result.getOrElse(Forbidden)
  }

  def getMessages = Authenticated(parse.json[String]) { implicit req =>
    val result = for {
      token <- req.session.get("token")
      user <- authService.getUserForToken(token)
      list <- profileService.getMessages(user.id, req.body)
    } yield Ok(Json.toJson(list))
    result.getOrElse(NotFound)
  }

  def addChat = Authenticated(parse.json[AddChatDTO]) { implicit req =>
    for {
      token <- req.session.get("token")
      user <- authService.getUserForToken(token)
    } yield profileService.addChat(user.id, req.body.receiver, req.body.text)
    Ok
  }


}
