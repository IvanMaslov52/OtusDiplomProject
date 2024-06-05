package models.repository

import models.CommentDTO
import models.dao.Comment

trait CommentRepository extends CrudRepository[Int, Comment] {
  def findCommentByTask(taskId: Int): List[CommentDTO]
}