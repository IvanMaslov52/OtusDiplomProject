package models.repository.impls

import models.dao.Chat
import models.repository.ChatRepository
import org.squeryl.Table

class ChatRepositoryImpl extends ChatRepository {

  override def defaultTable: Table[Chat] = Chat.chats

  override def find(sender_id: Int, receiver_id: Int): List[Chat] = transaction {
    from(defaultTable)(r => where((r.sender === sender_id and r.receiver === receiver_id) or (r.sender === receiver_id and r.receiver === sender_id)) select (r)).toList
  }
}
