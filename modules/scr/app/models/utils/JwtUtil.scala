package models.utils

import authentikat.jwt._
import models.dao.User

import java.time.LocalDateTime
import scala.util.Success

object JwtUtil {
  private val header = JwtHeader("HS256")
  private val secretKey = "uUgbRddiGvq1mlO88VQ+Rb4eG4g61sWntfXbkIpClH7ls6U9Gwtee6zST8GgMkCN"

  def generateToken(username: String): String = {
    val claimsSet = JwtClaimsSet(Map(
      "subject" -> username,
      "issuedAt" -> LocalDateTime.now().toString,
      "expiredAt" -> LocalDateTime.now().plusMinutes(5).toString
    ))
    JsonWebToken(header, claimsSet, secretKey)
  }

  private def isExpired(token: String): Boolean = token match {
    case JsonWebToken(_, claimsSet, _) => claimsSet.asSimpleMap match {
      case Success(value) => LocalDateTime.parse(value("expiredAt")).isAfter(LocalDateTime.now())
    }
  }
  def getUsername (token:String) :String = token match {
    case JsonWebToken(_, claimsSet, _) => claimsSet.asSimpleMap match {
      case Success(value) => value("subject")
    }
  }
  def validateToken(token: String): Boolean = JsonWebToken.validate(token, secretKey) && isExpired(token)

}
