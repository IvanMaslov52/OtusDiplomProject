package models.repository

import org.squeryl.dsl.ast.LogicalBoolean
import org.squeryl.{KeyedEntity, PrimitiveTypeMode, Table}

trait CrudRepository[K, Entity <: KeyedEntity[K]] extends PrimitiveTypeMode {

  def defaultTable: Table[Entity]

  def list(): List[Entity] = transaction(
    from(defaultTable)(e => select(e)).toList
  )

  def find(id: K): Option[Entity] = transaction(defaultTable.lookup(id))

  def insert(entity: Entity): Entity = transaction(defaultTable.insert(entity))

  def update(entity: Entity): Unit = transaction(defaultTable.update(entity))

  def delete(entity: Entity): Boolean = transaction(defaultTable.delete(entity.id))

  def insertList(list: List[Entity]): Unit = transaction(defaultTable.insert(list))

  def deleteWhere (whereClause: Entity => LogicalBoolean): Int = transaction(defaultTable.deleteWhere(whereClause))


}