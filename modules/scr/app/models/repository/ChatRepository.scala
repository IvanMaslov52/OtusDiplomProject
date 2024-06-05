package models.repository

import models.dao.Chat

trait ChatRepository extends CrudRepository[Int, Chat] {

  def find(sender_id: Int, receiver_id: Int): List[Chat]
}
