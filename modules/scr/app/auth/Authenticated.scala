package auth


import controllers.Assets.Ok
import models.utils.JwtUtil.validateToken
import play.api.mvc._

import scala.concurrent.Future

object Authenticated extends ActionBuilder[Request] {

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    request.session.get("token") match {
        case Some(value) if validateToken(value) => block(request)
        case _ => Future.successful(Ok(views.html.error("Сессия истекла")))
      }
  }
}
