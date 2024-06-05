package models.dto

import play.api.libs.json.Json

case class FriendsForm(who: String = "")

object FriendsForm {
  implicit val reads = Json.reads[FriendsForm]
  implicit val writes = Json.writes[FriendsForm]
}
