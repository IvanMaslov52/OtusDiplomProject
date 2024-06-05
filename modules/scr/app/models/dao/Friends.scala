package models.dao

import org.squeryl.PrimitiveTypeMode.compositeKey
import org.squeryl.dsl.CompositeKey2
import org.squeryl.{KeyedEntity, Schema, Table}

case class Friends(user_id: Int, friends_id: Int, status: String) extends KeyedEntity[CompositeKey2[Int, Int]] {
  override def id: CompositeKey2[Int, Int] = compositeKey(user_id, friends_id)
}

object Friends extends Schema {
  val friends: Table[Friends] = table[Friends]("friends")
  val users: Table[User] = table[User]("users")
}
