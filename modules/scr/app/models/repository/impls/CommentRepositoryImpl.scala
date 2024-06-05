package models.repository.impls

import models.CommentDTO
import models.dao.{Comment, User}
import models.repository.CommentRepository
import org.squeryl.Table

class CommentRepositoryImpl extends CommentRepository {

  override def defaultTable: Table[Comment] = Comment.comments

  override def findCommentByTask(taskId: Int): List[CommentDTO] =
    transaction {
      join(defaultTable, User.users)((c, u) =>
        where(c.task === taskId)
          select CommentDTO(u.username,c.task,c.description)
          on (c.creator === u.id)
      ).toList
    }
}
